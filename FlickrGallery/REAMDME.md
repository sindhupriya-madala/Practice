# FlickrGallery
  This app loads Images from Flickr and shows in galleryView

## Probelm and Solution
  Android Application that needs to fetch images and image meta data from Flickr using restfull and flickr API and display them in gallery view with image and image title and dimensions.

  Solution is to use Retrofit library to create restfull request to get images list and images meta data and with using GSON library to parse the json into java objects, After loading data from backend populate the content using Android RecyclerView and Adapter with ViewHolder.

## Technical Choices
  I have used MVVM architecture with LiveData because if a background process is going on and view needs to be updated with the result of background process ViewModel actually takes cares of the lifecycle of View thus proper view will be updated when configuration changes/(activity or fragment re-created) happen. 
  
  Retrofit is very powerfull library which I have used to create restfull services to fetch list of images, using retrofit its easy to create and yet very scalable.
  
Used Kotlin with Coroutines for creating background task to fetch images. Though experimental Coroutines in kotlin are light weight asynchronous task they are not threads and also multiple Coroutines can be created withing in single thread.
 

