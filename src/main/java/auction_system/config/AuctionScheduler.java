package auction_system.config;

import auction_system.entity.Auction;
import auction_system.enums.AuctionStatus;
import auction_system.repository.AuctionRepository;
import auction_system.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionScheduler {

    private final AuctionRepository auctionRepository;
    private final AuctionService auctionService;

    // Runs every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void closeExpiredAuctions() {
        List<Auction> liveAuctions = auctionRepository.findByStatus(AuctionStatus.LIVE);

        LocalDateTime now = LocalDateTime.now();

        for (Auction auction : liveAuctions) {
            if (auction.getEndTime() != null && auction.getEndTime().isBefore(now)) {
                try {
                    auctionService.closeAuction(auction.getId());
                    System.out.println("Automatically closed auction ID: " + auction.getId());
                } catch (Exception e) {
                    System.out.println("Failed to close auction ID: " + auction.getId());
                }
            }
        }
    }
}