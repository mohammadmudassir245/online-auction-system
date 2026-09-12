package auction_system.controller;

import auction_system.entity.Auction;
import auction_system.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestParam String title,
                                                 @RequestParam String description,
                                                 @RequestParam BigDecimal startingPrice,
                                                 @RequestParam Long sellerId,
                                                 @RequestParam String startTime,
                                                 @RequestParam String endTime) {

        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);

        Auction auction = auctionService.createAuction(title, description, startingPrice, sellerId, start, end);
        return ResponseEntity.ok(auction);
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuction(@PathVariable Long id) {
        return ResponseEntity.ok(auctionService.getAuctionById(id));
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<Auction> startAuction(@PathVariable Long id) {
        Auction auction = auctionService.startAuction(id);
        return ResponseEntity.ok(auction);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Auction> closeAuction(@PathVariable Long id) {
        Auction auction = auctionService.closeAuction(id);
        return ResponseEntity.ok(auction);
    }
}