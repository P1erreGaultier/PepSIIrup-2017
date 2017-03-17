package com.service.event;

/**
 * Event Class with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
<<<<<<< HEAD

=======
>>>>>>> cb730ff8f84507a7de34036b1612bfe96c4c4ded
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="Event")
@JsonRootName("Event")
public class Event implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Eventid",unique=true, nullable=false)
	private int eventId;
	
	@JsonProperty("Name")
	@Column(name = "Name")
	private String name;
	
	@JsonProperty("Datestart")
	@Column(name = "Datestart")
	private Date dateStart;
	
	@JsonProperty("Dateend")
	@Column(name = "Dateend")
	private Date dateEnd;
	
	@JsonProperty("Placeid")
	@Column(name = "Placeid")
	private String placeID;
	
	@JsonProperty("Description")
	@Column(name = "Description")
	private String description;
	
	@JsonProperty("Image")
	@Column(name = "Image")
	private String image;
	
	@JsonProperty("Iscanceled")
	@Column(name = "Iscanceled")
	private int isCanceled;
	
	@JsonProperty("Owner")
	@Column(name = "Owner")
	private int owner;
	

	public Event(){
		//JPA need empty constructor
	} 
	
	public Event(String name) {
		this.name = name;
		this.dateStart = new Date();
		this.dateEnd = new Date();
		this.placeID = "17";
		this.description = "too long";
		this.isCanceled = 0;
		this.owner = 2;
	}
	
	
	public Event(String name, Date dateStart, Date dateEnd, String placeID, String description, int isCanceled,
			int owner) {
		super();
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.placeID = placeID;
		this.description = description;
		this.isCanceled = isCanceled;
		this.owner = owner;
	}

	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int id){
		this.eventId = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getPlaceID() {
		return placeID;
	}

	public void setPlaceID(String placeID) {
		this.placeID = placeID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(int isCanceled) {
		this.isCanceled = isCanceled;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
			return false;
		return true;
	}
	
}
