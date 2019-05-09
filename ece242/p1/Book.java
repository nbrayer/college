class Book extends MediaItem
{  protected String author;
   
   public Book(String mediaType,String itemTitle,String itemReference,double itemPrice,String author)
   {  super(mediaType,itemTitle,itemReference,itemPrice);
      this.author=author;
   }
}