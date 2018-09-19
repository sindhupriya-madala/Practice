# FlickrGallery
This app loads Images from Flickr and shows in galleryView

## Probelm and Solution
Android Application that needs to fetch images and image meta data from Flickr using restfull and flickr API and display them in gallery view with image and image title and dimensions.

Solution is to use Retrofit library to create restfull request to get images list and images meta data and with using GSON library to parse the json into java objects, After loading data from backend populate the content using Android RecyclerView and Adapter with ViewHolder.
