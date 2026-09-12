package auction_system.service;

import auction_system.entity.Auction;
import auction_system.entity.Bid;
import auction_system.entity.User;
import auction_system.enums.AuctionStatus;
import auction_system.repository.AuctionRepository;
import auction_system.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final UserService userService;

    public Auction createAuction(String title, String description,
                                 BigDecimal startingPrice, Long sellerId,
                                 LocalDateTime startTime, LocalDateTime endTime) {

        User seller = userService.findById(sellerId);

        Auction auction = Auction.builder()
                .title(title)
                .description(description)
                .startingPrice(startingPrice)
                .currentPrice(startingPrice)
                .minIncrement(BigDecimal.valueOf(10))
                .startTime(startTime)
                .endTime(endTime)
                .status(AuctionStatus.UPCOMING)
                .seller(seller)
                .build();

        return auctionRepository.save(auction);
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public Auction getAuctionById(Long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
    }

    public Auction startAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (auction.getStatus() != AuctionStatus.UPCOMING) {
            throw new RuntimeException("Only UPCOMING auctions can be started");
        }

        auction.setStatus(AuctionStatus.LIVE);
        return auctionRepository.save(auction);
    }

    public Auction closeAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (auction.getStatus() != AuctionStatus.LIVE) {
            throw new RuntimeException("Only LIVE auctions can be closed");
        }

        // Find highest bid
        List<Bid> bids = bidRepository.findByAuctionIdOrderByAmountDesc(auctionId);

        if (!bids.isEmpty()) {
            Bid highestBid = bids.get(0);
            auction.setWinner(highestBid.getBidder());
        }

        auction.setStatus(AuctionStatus.CLOSED);
        return auctionRepository.save(auction);
    }
}