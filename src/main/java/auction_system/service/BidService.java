package auction_system.service;

import auction_system.entity.Auction;
import auction_system.entity.Bid;
import auction_system.entity.User;
import auction_system.enums.AuctionStatus;
import auction_system.repository.AuctionRepository;
import auction_system.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public Bid placeBid(Long auctionId, Long bidderId, BigDecimal amount) {

        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        User bidder = userService.findById(bidderId);

        // Validations
        if (auction.getStatus() != AuctionStatus.LIVE && auction.getStatus() != AuctionStatus.UPCOMING) {
            throw new RuntimeException("Auction is not open for bidding");
        }

        if (amount.compareTo(auction.getCurrentPrice().add(auction.getMinIncrement())) < 0) {
            throw new RuntimeException("Bid amount is too low. Minimum required: " +
                    auction.getCurrentPrice().add(auction.getMinIncrement()));
        }

        if (auction.getSeller().getId().equals(bidderId)) {
            throw new RuntimeException("Seller cannot bid on their own auction");
        }

        // Create Bid
        Bid bid = Bid.builder()
                .auction(auction)
                .bidder(bidder)
                .amount(amount)
                .bidTime(LocalDateTime.now())
                .build();

        bidRepository.save(bid);

        // Update current price
        auction.setCurrentPrice(amount);
        auctionRepository.save(auction);

        // Broadcast the new bid to everyone watching this auction
        messagingTemplate.convertAndSend("/topic/auction/" + auctionId, bid);

        return bid;
    }

    public List<Bid> getBidsByAuction(Long auctionId) {
        return bidRepository.findByAuctionIdOrderByAmountDesc(auctionId);
    }
}