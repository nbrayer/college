public class MediaItem
{  private String mediaType;
   private String itemTitle;
   private String itemReference;
   private double itemPrice;  
   private String author;
   private String director;
   private String actor;
   protected String result; 
   
   public MediaItem(String mediaType,String itemTitle,String itemReference,double itemPrice)
   {  this.mediaType=mediaType;
      this.itemTitle=itemTitle;
      this.itemReference=itemReference;
      this.itemPrice=itemPrice;
   }
   
   protected void setMediaType(String mediaType)
   {  this.mediaType=mediaType;
   }  
   protected void setTitle(String itemTitle)
   {  this.itemTitle=itemTitle;
   }
   protected void setReferenceID(String itemReference)
   {  this.itemReference=itemReference;
   }
   protected void setPrice(double itemPrice)
   {  this.itemPrice=itemPrice;
   }
   protected void setAuthor(String author)
   {  this.author=author;
   }
   protected void setDirector(String director)
   {  this.director=director;
   }
   protected void setActor(String actor)
   {  this.actor=actor;
   }
   
   public String getMediaType()
   {  return(mediaType);
   }
   public String getTitle()
   {  return(itemTitle);
   }
   public String getReferenceID()
   {  return(itemReference);
   }
   public double getPrice()
   {  return(itemPrice);
   }
   public String getAuthor()
   {  return(author);
   }
   public String getDirector()
   {  return(director);
   }
   public String getActor()
   {  return(actor);
   }

   
   public String WhoAmI()
   {  result=new String("Title: "+itemTitle+" (Ref: "+itemReference+", Price: "+itemPrice+");\n"); 
      if(mediaType.compareTo("Book")==0)
         result+="Author: "+author;
      else if(mediaType.compareTo("Movie")==0)
         result+="Movie Director: "+director+"; Main Actor: "+actor;   
      return(result);
   }
}