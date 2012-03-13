package de.objectcode.data.dataobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;



@Entity
public class Hotel extends DomainObject implements Serializable
{
   private Long id;
   private String name;
   private String address;
   private String city;
   private String state;
   private String zip;
   private String country;
   private BigDecimal price;
   
   
   public Hotel(){}
   public Hotel(final int price, final String name, final String address,
           final String city, final String state, final String zip, final String country)
   {
       this.price = new BigDecimal(price);
       this.name = name;
       this.address = address;
       this.city = city;
       this.state = state;
       this.zip = zip;
       this.country = country;
   }

   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }
   public void setId(Long id)
   {
      this.id = id;
   }
   
   @Size(max=50) //@NotNull
   public String getName()
   {
      return name;
   }
   public void setName(String name)
   {
      this.name = name;
   }
   
   @Size(max=100) //@NotNull
   public String getAddress()
   {
      return address;
   }
   public void setAddress(String address)
   {
      this.address = address;
   }
   
   @Size(max=40) //@NotNull
   public String getCity()
   {
      return city;
   }
   public void setCity(String city)
   {
      this.city = city;
   }
   
   @Size(min=4, max=6) //@NotNull
   public String getZip()
   {
      return zip;
   }
   public void setZip(String zip)
   {
      this.zip = zip;
   }
   
   @Size(min=2, max=10) //@NotNull
   public String getState()
   {
      return state;
   }
   public void setState(String state)
   {
      this.state = state;
   }
   
   @Size(min=2, max=40) //@NotNull
   public String getCountry()
   {
      return country;
   }
   public void setCountry(String country)
   {
      this.country = country;
   }

   @Column(precision=6, scale=2)
   public BigDecimal getPrice()
   {
      return price;
   }
   public void setPrice(BigDecimal price)
   {
      this.price = price;
   }
   
   @Override
   public String toString()
   {
      return "Hotel(" + name + "," + address + "," + city + "," + zip + ")";
   }
}
