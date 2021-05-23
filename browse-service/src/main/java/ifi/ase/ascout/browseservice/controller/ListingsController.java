package ifi.ase.ascout.browseservice.controller;

import ifi.ase.ascout.browseservice.data.model.ListingsDetailModel;
import ifi.ase.ascout.browseservice.data.model.ListingsModel;
import ifi.ase.ascout.browseservice.data.repository.ListingsDetailRepository;
import ifi.ase.ascout.browseservice.data.repository.ListingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/browse")
public class ListingsController {

    // default page number
    private static final String DEFAULT_PAGING = "true";

    // default page number
    private static final String DEFAULT_PAGE_NUMBER = "0";

    // default page size
    private static final String DEFAULT_PAGE_SIZE = "10";

    private List<ListingsModel> listings;

    private List<ListingsDetailModel> listingsDetail;

    @Autowired
    private ListingsRepository listingsRepository;

    @Autowired
    private ListingsDetailRepository listingsDetailRepository;

    @GetMapping("/neighbourhood={neighbourhood}")
    public ResponseEntity<List<ListingsModel>> getByNeighbourhood(@PathVariable String neighbourhood,
                                                                  @RequestParam(defaultValue = DEFAULT_PAGING) String paging,
                                                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {

        boolean isPaging = Boolean.parseBoolean(paging);
        if(isPaging) {
            listings = listingsRepository.findByNeighbourhood(neighbourhood, PageRequest.of(page, size));
        }else{
            listings = listingsRepository.findByNeighbourhood(neighbourhood);
        }

        if (listings.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).body(listings);
    }

    @PostMapping(path = "/neighbourhoods", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ListingsModel>> getByNeighbourhoods(@RequestBody List<String> neighbourhoods,
                                                                   @RequestParam(defaultValue = DEFAULT_PAGING) String paging,
                                                                   @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {

        boolean isPaging = Boolean.parseBoolean(paging);
        if(isPaging) {
            listings = listingsRepository.findByNeighbourhoodIn(neighbourhoods, PageRequest.of(page, size));
        }else{
            listings = listingsRepository.findByNeighbourhoodIn(neighbourhoods);
        }

        if (listings.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).body(listings);
    }

    @GetMapping("/allListings")
    public ResponseEntity<List<ListingsModel>> getAllListings() {

        listings = listingsRepository.findAll();
        if (listings.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).body(listings);
    }

    @GetMapping("/allListingsDetail")
    public ResponseEntity<List<ListingsDetailModel>> getAllListingsDetail() {

        listingsDetail = listingsDetailRepository.findAll();
        if (listingsDetail.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).body(listingsDetail);
    }
}
