package edu.ncsu.csc216.pack_scheduler.course;

/**
 * superclass of Course and Event, holds methods and fields that are similar to both derived classes.
 * The fields are UPPER_HOUR, UPPER_MINUTE, title, meetingDays, startTime, and endTime.  The methods are
 * getTitle, setTitle, getMeetingDays, getStartTime, getEndTime, setMeetingDaysAndTime, getMeetingString,
 * getTimeString, getShortDisplayArray, getLongDisplayArray, hashCode, and equals.
 * @author Maisa Little
 * 
 */
public abstract class Activity implements Conflict {

	/** maximum hour */
	private static final int UPPER_HOUR = 24;
	/** maximum minute */
	private static final int UPPER_MINUTE = 60;
	/** activity title. */
	private String title;
	/** activity meeting days */
	private String meetingDays;
	/** activity starting time */
	private int startTime;
	/** activity ending time */
	private int endTime;

	/**
	 * default constructor for Activity object
	 * @param title title of activity
	 * @param meetingDays meeting days
	 * @param startTime activity start time
	 * @param endTime activity end time
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }

	/**
	 * gets the activity title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * sets the activity title
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null or empty string
	 */
	public void setTitle(String title) {
		//using Conditional 2 optionS
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
	
		this.title = title;
	}

	/**
	 * gets the activity meeting days
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * gets the activity start time
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * gets the activity end time
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * this sets the meeting days and time
	 * @param meetingDays days the activity meets
	 * @param startTime time activity meeting starts
	 * @param endTime time activity meeting ends
	 * @throws IllegalArgumentException if meeting days are null, empty string,
	 * contain an invalid letter, = "A" with start and end time other than 0, or 
	 * times are not logical (i.e. end before start, etc.)
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		//meeting days is null or empty
	   if (meetingDays == null || meetingDays.length() == 0) {
	      throw new IllegalArgumentException("Invalid meeting days and times.");
	   }
	      
	   if (meetingDays.contains("A")) { // Arranged
	      if (startTime != 0 || endTime != 0) { //not supposed to have a time if "A"
		     throw new IllegalArgumentException("Invalid meeting days and times.");
	      }
	      else {
	    	  this.meetingDays = meetingDays;
		      this.startTime = 0;
		      this.endTime = 0;
	      }
	   }
	   else { //not arranged
	      int m = 0;
	      int t = 0;
	      int w = 0;
	      int h = 0;
	      int f = 0;
	      int s = 0;
	      int u = 0;
	      
		  for (int i = 0; i < meetingDays.length(); i++) {
		     if (meetingDays.charAt(i) == 'M') { //monday
		    	 m++;
		     }
		     else if (meetingDays.charAt(i) == 'T') { //tuesday
		    	 t++;
		     }
		     else if (meetingDays.charAt(i) == 'W') { //wednesday
		    	 w++;
		     }
		     else if (meetingDays.charAt(i) == 'H') { //thursday
		    	 h++;
		     }
		     else if (meetingDays.charAt(i) == 'F') { //friday
		    	 f++;
		     }
		     else if (meetingDays.charAt(i) == 'S') { //saturday
		    	 s++;
		     }
		     else if (meetingDays.charAt(i) == 'U') { //sunday
		    	 u++;
		     }
		     else { //invalid letter
			    throw new IllegalArgumentException("Invalid meeting days and times.");
			 }
	      }
		
		  if (m > 1 || t > 1 || w > 1 || h > 1 || f > 1 || s > 1 || u > 1) { // checks for duplicates
		     throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
			 
		  //split start and end times into hours and minutes
		  int startHours = startTime / 100;
		  int startMins = startTime % 100;
		  int endHours = endTime / 100;
		  int endMins = endTime % 100;
		  
		  if (startTime > endTime) { //start time is later than end time
			  throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		  
		  if (startHours  <  0 || startHours >=  UPPER_HOUR) { // not between 0 and 23, inclusive
			  throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		
		  if (startMins  <  0 || startMins >=  UPPER_MINUTE) { // not between 0 and 59, inclusive
			  throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		  
		  if (endHours  <  0 || endHours >=  UPPER_HOUR) { // not between 0 and 23, inclusive
			  throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
		
		  if (endMins  <  0 || endMins >=  UPPER_MINUTE) { // not between 0 and 59, inclusive
			  throw new IllegalArgumentException("Invalid meeting days and times.");
		  }
	   	  
		//everything is valid and works together!
		  this.meetingDays = meetingDays;
		  this.startTime = startTime;
		  this.endTime = endTime;
	   }
	}

	/**
	 * gets the meeting details
	 * @return meeting details as D[D] HH:MM w/ AM/PM -HH:MM w/ AM/PM
	 */
	public String getMeetingString() {
		if ("A".equals(this.meetingDays)) { //if arranged
			return "Arranged";
		}
		else {
			String startTimeStr = getTimeString(this.startTime);
			String endTimeStr = getTimeString(this.endTime);
			return this.meetingDays + " " + startTimeStr + "-" + endTimeStr;
		}
	}

	/**
	 * converts military time to regular time
	 * @param time time of day
	 * @return String representing time HH:MM with AM/PM attached at end
	 */
	private String getTimeString(int time) {
		int militaryHours = time / 100;
		int militaryMins = time % 100;
		
		int hours = -1;
		String minutes;
		
		String timeOfDay = ""; //AM or PM
		
		if (militaryHours > 12) { // after lunch
			hours = militaryHours - 12;
			timeOfDay = "PM";
		}
		else if (militaryHours == 12) { // lunch time
			hours = 12;
			timeOfDay = "PM";
		}
		else if (militaryHours == 0){ //midnight
			hours = 12;
			timeOfDay = "AM";
		}
		else if (militaryHours < 12) { //before lunch
			hours = militaryHours;
			timeOfDay = "AM";
		}
		
		if (militaryMins  <  10) { //add 0 to time if it is only a single digit to match HH:MM
			minutes = "0" + militaryMins;
		}
		else { //keep two digits in minutes
			minutes = militaryMins + "";
		}
		
		return hours + ":" + minutes + timeOfDay;
		
	}
	
	/**
	 * Gets short list of Activity info
	 * @return array of length 4 containing the Course name, section, title, and meeting string.
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * gets long list of Activity info
	 * @return array of length 7 containing the Course name, section, title, credits, instructorId, meeting string, empty string (for a field that Event will have that Course does not)
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * hash code override for Activity
	 * @return int of hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * equals override for Activity
	 * @param obj is an object being compared
	 * @return true of obj is the same as the Activity object; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * abstract class checking to see if the objects are duplicates
	 * @param activity object being compared
	 * @return true if equal, false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	/**
	 * method to override a conflict check
	 * @param possibleConflictingActivity is an activity that may conflict time with another
	 * @throws ConflictException if the activity times overlap
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		int thisStart = this.getStartTime();
		int thisEnd = this.getEndTime();
		String thisMeetingDays = this.getMeetingDays();
		
		int thatStart = possibleConflictingActivity.getStartTime();
		int thatEnd = possibleConflictingActivity.getEndTime();
		String thatMeetingDays = possibleConflictingActivity.getMeetingDays();

		//to see if they meet on any of the same days, ignoring 'A'rranged 
		for (int i = 0; i < thisMeetingDays.length(); i++) {
			for (int j = 0; j < thatMeetingDays.length(); j++) {
				
				//get current days on list
				String thisDay = thisMeetingDays.charAt(i) + "";
				String thatDay = thatMeetingDays.charAt(j) + "";
				
				if (thisDay.equals(thatDay) && !("A".equals(thisDay))) { //now, check the times below
					if (thisStart >= thatStart && thisStart <= thatEnd) {
						throw new ConflictException();
					}
					if (thatStart >= thisStart && thatStart <= thisEnd) {
						throw new ConflictException();
					}
				}
			}
		}
	}
}