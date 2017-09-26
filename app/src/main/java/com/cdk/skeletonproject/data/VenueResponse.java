package com.cdk.skeletonproject.data;

public class VenueResponse {

   private Double lat;
   private Double lng;
   private String uri;
   private MetroAreaResponse metroArea;
   private String displayName;
   private Long id;

   public Double getLat() {
      return lat;
   }

   public Double getLng() {
      return lng;
   }

   public String getUri() {
      return uri;
   }

   public MetroAreaResponse getMetroArea() {
      return metroArea;
   }

   public String getDisplayName() {
      return displayName;
   }

   public Long getId() {
      return id;
   }
}
