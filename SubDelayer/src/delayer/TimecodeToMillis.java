package delayer;
public class TimecodeToMillis {
	
public int delay = 0;
public String timeCode = null;

	public String changeTimecode(String timeCode, int delay) {
		this.delay = delay;
		this.timeCode = timeCode;
		
		// hh:mm:ss,lll
		// "02:02:14,443"
		// s.length() -> 12
		
		int lll = Integer.parseInt(timeCode.substring(9, 12));
		int ss = Integer.parseInt(timeCode.substring(6, 8));
		int mm = Integer.parseInt(timeCode.substring(4, 5));
		int hh = Integer.parseInt(timeCode.substring(0, 2));

		/*
		 * 1 Second =   1,000 Milliseconds
		 * 1 Minute =  60,000 Milliseconds
		 * 1 Hour = 3,600,000 Milliseconds
		 */
		int inMilliStart = lll + ss*1000 + mm*60000 + hh*3600000 + delay;
		
        	// Hours
		String hhToSrt = "" + Math.floorDiv(inMilliStart, 3600000);
		String hhToSrtres = (hhToSrt.length()==1) ? "0" + hhToSrt : hhToSrt; 
		
		inMilliStart = inMilliStart - Integer.parseInt(hhToSrt)*3600000;
		
		// Mins
		String mmToSrt = "" + Math.floorDiv(inMilliStart, 60000);
		String mmToSrtres = (mmToSrt.length()==1) ? "0" + mmToSrt : mmToSrt; 
		
		inMilliStart = inMilliStart - Integer.parseInt(mmToSrt)*60000;
		
		// Secs
		String ssToSrt = "" + Math.floorDiv(inMilliStart, 1000);
		String ssToSrtres = (ssToSrt.length()==1) ? "0" + ssToSrt : ssToSrt; 
		
		inMilliStart = inMilliStart - Integer.parseInt(ssToSrt)*1000;
		
		//Millis - the rest of the millisecods goes after the , sign
		int lengthOfMillis = Integer.toString(inMilliStart).length();
		String lllToSrtres = null;
				
		if(lengthOfMillis==1) {
			lllToSrtres = "00" + inMilliStart;
		} else if (lengthOfMillis==2) {
			lllToSrtres = "0" + inMilliStart;
		} else { // length: 3
			lllToSrtres = "" + inMilliStart;
		}
		
		//Full timecode
		String codeNew = hhToSrtres + ":" + mmToSrtres + ":" + ssToSrtres + "," + lllToSrtres;
		return codeNew;
	}

}
