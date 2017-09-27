package net.joelreeves.flickrphotodemo.services;

public class FlickrPhotoRepository {

    private final FlickrService flickrService;

    private static final String API_KEY = "c28ab4cafc09aff5feffbabcbc92af9f";
    private static final String API_SECRET = "aa0fc0eaf0b6a763";

    public FlickrPhotoRepository(FlickrService flickrService) {
        this.flickrService = flickrService;
    }


}
