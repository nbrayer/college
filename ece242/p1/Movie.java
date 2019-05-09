class Movie extends MediaItem
{  protected String director;
   protected String actor;
   
   public Movie(String mediaType,String itemTitle,String itemReference,double itemPrice,String director,String actor)
   {  super(mediaType,itemTitle,itemReference,itemPrice);
      this.director=director;
      this.actor=actor;
   }
}