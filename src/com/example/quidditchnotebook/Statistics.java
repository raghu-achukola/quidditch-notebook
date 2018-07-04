package com.example.quidditchnotebook;

import java.util.ArrayList;

import android.util.Log;

public class Statistics 
{
	public static final int GAME_TIE =0;
	public static final int GAME_WOSR=1;
	public static final int GAME_WISR=2;
	public static final int GAME_LISR=3;
	public static final int GAME_LOSR=4;
	static boolean loseExtra;
	static boolean maintExtra;
	public static double[] process(ArrayList<Game> games)
	{
		int countBU=0;
		int BUS = 0;
		int BUA = 0;
		int BUBB = 0;
		double[] result = new double[40];
		
		/*0-11 Goal/Attempt
		 * 12-17 Success%
		 *  18 Length
		 *  19 BDC
		 *  20-21 Balanced Indices
		 *  22-24 One-Two O/D/C
		 *  25 -26 Raw
		 *  27 No Bludgers
		 *  28-29 Obtention/Retention
		 *  30-32 BUS/BUA/BUBB
		 */
		int wins=0;
		int losses = 0;
		int draws = 0;
		int wisr =0;
		int lisr = 0;
		for (int i=0;i<games.size();i++)
		{
			double[]gameResult=process(games.get(i).getData());
			countBU++;
			result[0]+= gameResult[0];
			result[1]+= gameResult[1];
			result[2]+= gameResult[2];
			result[3]+= gameResult[3];
			result[4]+= gameResult[4];
			result[5]+= gameResult[5];
			result[6]+= gameResult[6];
			result[7]+= gameResult[7];
			result[8]+= gameResult[8];
			result[9]+= gameResult[9];
			result[10]+= gameResult[10];
			result[11]+= gameResult[11];
			result[18]+=gameResult[18];
			result[28]+=gameResult[34];
			result[29]+=gameResult[33];
			if(Math.floor(gameResult[25])==1)
			{
				BUBB++;
			}
			if (Math.floor(gameResult[23])==1)
			{
				BUS++;
			}
			else if (Math.floor(gameResult[23])==2)
			{
				BUA++;
			}
			if((int)gameResult[38]==1)
			{
				countBU++;
				if(gameResult[25]-Math.floor(gameResult[25])<.5)
				{
					BUBB++;
					
				}
				if(gameResult[23]-Math.floor(gameResult[23])<.5)
				{
					if(gameResult[23]-Math.floor(gameResult[23])>.2)
					{BUS++;}
				}
				else
				{
					BUA++;
				}
			}
			if(gameResult[35]>gameResult[36])
			{
				wins++;
			}
			else if (gameResult[35]==gameResult[36])
			{
				draws++;
			}
			else
			{
				losses++;
			}
			if(gameResult[37]==Statistics.GAME_WISR)
			{
				wisr++;
			}
			else if (gameResult[37] == Statistics.GAME_LISR)
			{
				lisr++;
			}
		}
		result[12]=result[1]==0? -1:result[0]/result[1];
		result[13]=result[3]==0? -1:result[2]/result[3];
		result[14]=result[5]==0? -1:result[4]/result[5];
		result[15]=result[7]==0? -1:result[6]/result[7];
		result[16]=result[9]==0? -1:result[8]/result[9];
		result[17]=result[11]==0? -1:result[10]/result[11];
		result[18]=result[18]/games.size();
		result[28]=result[28]/games.size();
		result[29]=result[29]/games.size();
		result[19]=(result[1]+result[3]+result[11])/(result[1]+result[3]+result[5]+result[7]+result[9]+result[11]); //BDC
		if(result[3]==0||result[5]==0)
		{
			result[20]=-1;	 //For games with incalculable index
		}
		else
		{
			if(result[1]==0)
			{
				result[20]=110*result[13]+110*result[14]+10;
			}
			else
			{
				result[20]=110*result[13]+110*result[14]+20*result[12];	
			}
		}
		//End Logic for Result 19 (Balanced Index 1st Team)
		//Start Logic for Result 20 (Balanced Index 2nd Team)
		if(result[9]==0||result[11]==0)
		{
			result[21]=-1;	 //For games with incalculable index
		}
		else
		{
			if(result[7]==0)
			{
				result[21]=110*result[16]+110*result[17]+10;
			}
			else
			{
				result[21]=110*result[17]+110*result[16]+20*result[15];	
			}
		}
		if(result[3]==0||result[5]==0)
		{
			result[22]=-1;	 //For games with incalculable one-two
		}
		else
		{
			result[22]=250*(result[13]-result[14]);
		}
		if(result[9]==0||result[11]==0)
		{
			result[23]=-1;	 //For games with incalculable one-two
		}
		else
		{
			result[23]=250*(result[16]-result[17]);
		}
		if(result[23]!=-1&&result[22]!=-1)
		{result[24]=result[22]+result[23];}
		else
		{result[24]= -1;}
		try{result[25] = (result[0]+result[2]+result[4])/(result[1]+result[3]+result[5]);}catch(Exception e){result[25]=-1;}
		try{result[26] = (result[6]+result[8]+result[10])/(result[7]+result[9]+result[11]);
		result[27] = (result[7])/(result[7]+result[9]+result[11]);}
		catch(Exception e){result[25]=-1;}
		if(countBU!=0)
		{result[30] = (double)BUS/(double)countBU;
		result[31] = (double)BUA/(double)countBU;
		result[32] = (double)BUBB/(double)countBU;
		Log.w("stat",String.valueOf(BUS));
		Log.w("stat",String.valueOf(BUA));
		Log.w("stat",String.valueOf(BUBB));
		}
		else{result[30]=-1;result[31]=-1;result[32]=-1;}
		result[35] = wisr;
		result[36] = lisr;
		result[37] = wins;
		result[38] = draws;
		result[39]= losses;
		return result;
	}
	public static double[] process(String d)
	{
		double [] result = new double [39];
		int pointer = -1;
		result[23]=Double.parseDouble(d.substring(0,1));
		result[25]=Double.parseDouble(d.substring(1,2));
		pointer = Integer.parseInt(d.substring(2,3));
		String team1="";
		String team2 = "";
		int counter = 0;
		String regularTime = d.substring(0,d.indexOf("S")-1);

		for(int i=3;i<regularTime.length();i++)
		{
			if(Character.isDigit(regularTime.charAt(i)))
			{
			
				if((counter+pointer)%2==1)
				{
					
					team1 = team1+regularTime.substring(i,i+1);
				}
				else
				{
					team2 = team2+regularTime.substring(i,i+1);;
				}
				counter++;
			}
			
		}

		result[18]=(double) counter;
		result[26]=(d.charAt(d.indexOf('S')-1)=='F')?1:2;
	//System.out.println(team1);
	//System.out.println(team2);
		double[] t1 = scanQuaffle(team1);
		double [] t2 = scanQuaffle(team2);
		for(int i=0;i<6;i++)
		{
			result[i]=t1[i];
			result[i+6]=t2[i];
		}
		result[29] = result[1]/(result[1]+result[3]+result[5]);
		result[30] = result[7]/(result[7]+result[9]+result[11]);
		result[24] = (result[1]+result[3]+result[11])/result[18];
		result[27]=(Math.floor(result[23])==1)?(result[0]+result[2]+result[4]+1)*10:(result[0]+result[2]+result[4])*10;
		result[28]=(Math.floor(result[23])==2)?(result[6]+result[8]+result[10]+1)*10:(result[6]+result[8]+result[10])*10;
		if(d.length()>d.indexOf("S")+1)
		{
			String ot1="";
			String ot2="";
			result[38]=1;
			int counterOT = 0;
			String prelimOT = d.substring(d.indexOf("S")+1,d.indexOf("S")+4);
			String otData = d.substring(d.indexOf("S")+1,d.length());
			if(Integer.parseInt(prelimOT.substring(0,1))==0)
			{

			}
			else if(Integer.parseInt(prelimOT.substring(0,1))==1)
			{
				result[23]+=0.33;
			}
			else if(Integer.parseInt(prelimOT.substring(0,1))==2)
			{
				result[23]+=0.66;
			}
			if(Integer.parseInt(prelimOT.substring(1,2))==1)
			{
				result[25]+=0.33;
			}
			else if(Integer.parseInt(prelimOT.substring(1,2))==2)
			{
				result[25]+=0.66;
			}
			int pointerOT = Integer.parseInt(prelimOT.substring(2,3));
			for(int i =3; i<otData.length();i++)
			{
				if(Character.isDigit(otData.charAt(i)))
				{


					if((counterOT+pointerOT)%2==1)
					{
						ot1= ot1+otData.substring(i,i+1);
					}
					else
					{
						ot2 = ot2 +otData.substring(i,i+1);
					}
					counterOT++;
				}

			}
			result[18]+=counterOT;
			double[] overtimeQuaffle1 = scanQuaffle(ot1);
			double[] overtimeQuaffle2 = scanQuaffle(ot2);
			for(int i=0;i<6;i++)
			{
				result[i]+=overtimeQuaffle1[i];
				result[i+6]+=overtimeQuaffle2[i];

			}	

		}
		else
		{
			result[38]=0;
		}
		//End logic for Results 0-11


		//Start Logic for Results 12-17 (Quaffle %)
		for(int i=0;i<6;i++)
		{
			if(result[2*i+1]==0)
			{
				result[i+12]=-1;	 //For games with no drives against 0
			}
			else{
				result[i+12]=result[2*i]/result[2*i+1];
			}
		}
		if(result[3]==0||result[5]==0)
		{
			result[19]=-1;	 //For games with incalculable index
		}
		else
		{
			if(result[1]==0)
			{
				result[19]=110*result[13]+110*result[14]+10;
			}
			else
			{
				result[19]=110*result[13]+110*result[14]+20*result[12];	
			}
		}
		//End Logic for Result 19 (Balanced Index 1st Team)
		//Start Logic for Result 20 (Balanced Index 2nd Team)
		if(result[9]==0||result[11]==0)
		{
			result[20]=-1;	 //For games with incalculable index
		}
		else
		{
			if(result[7]==0)
			{
				result[20]=110*result[16]+110*result[17]+10;
			}
			else
			{
				result[20]=110*result[17]+110*result[16]+20*result[15];	
			}
		}
		result[21]=(result[0]+result[2]+result[4])/(result[1]+result[3]+result[5]);
		result[22]=(result[6]+result[8]+result[10])/(result[7]+result[9]+result[11]);
	if(result[38]==1)
	{
		if(d.charAt(d.length()-2)=='O'){result[26]+=0.33;}
		else if(d.charAt(d.length()-2)=='P'){result[26]+=0.66;}
	}
	String d2 = regularTime.substring(3,regularTime.length());
	ArrayList<Integer> maint = new ArrayList<Integer>();
	ArrayList<Integer> lose = new ArrayList<Integer>();
	int countm=0;
	int countl=0;
	int changes=0;
	for(int i=0;i<d2.length();i++)
	{

		if(d2.charAt(i)=='T')
		{
			maint.add(Integer.valueOf(countm));
			countm=-6500;
			countl=-1;
			changes++;
		}
		if(d2.charAt(i)=='R')
		{
			lose.add(Integer.valueOf(countl));
			countl=-6500;
			countm=-1;
			changes++;
		}
		
	countm++;
	countl++;
	}


	//Start Logic Result 32, 33, 34 (BS G/I)
	if(changes==0)
	{
		maint.add(Integer.valueOf(countm));
		result[32]=result[18];
		if(result[25]==1)
		{


			result[33]=result[18];
			result[34]=-1;

		}
		else
		{
			result[34]=result[18];
			result[33]=-1;
		}
	}
	else
	{
		if(countm>=0)
		{
			maintExtra = true;
			loseExtra = false;
			maint.add(Integer.valueOf(countm));
			result[32]=averageIntg(lose, maint);


		}
		else
		{
			maintExtra = false;
			loseExtra = true;
			lose.add(Integer.valueOf(countl));
			result[32]=averageIntg(maint, lose);
		}
		if((int)Math.floor(result[25])==1)
		{

		
			result[34]=medianIntg(maint,maintExtra);
			result[33]=medianIntg(lose,loseExtra);

		}
		else
		{
			result[33]=medianIntg(maint,maintExtra);
			result[34]=medianIntg(lose,loseExtra);
		}
	}
	//End Logic Result 32/33/34 (BS G/I)
	result[31]=changes; //Result 31 (Blugder Fluidity, Game)
int snitch=0;
	result[35]=10*(result[0]+result[2]+result[4]);
	result[36]=10*(result[6]+result[8]+result[10]);
	result[36] = (Math.floor(result[26])==1)? result[36]:result[36]+30;
	result[35] = (Math.floor(result[26])==2)? result[35]:result[35]+30;
	
	if((Math.floor(result[26])==1)){snitch++;}else{snitch--;}
	if(Math.round(100*(result[26]-Math.floor(result[26])))==33)
	{
		snitch++;
		result[35]+=30;
	}
	else if(Math.round(100*(result[26]-Math.floor(result[26])))==66)
	{	snitch--;
		result[36]+=30;
	}
	if(Math.round(100*(result[23]-Math.floor(result[23])))==33)
	{
		
		result[35]+=10;
	}
	else if(Math.round(100*(result[23]-Math.floor(result[23])))==66)
	{
		result[36]+=10;
	}
	if(Math.floor(result[23])==1)
	{
		result[35]+=10;
	}
	if(Math.floor(result[23])==2)
	{
		result[36]+=10;
	}

	//RESULT 37 (WISR/WOSR)
	if(result[35]==result[36])
	{
		result[37]=0;
	}
	else
	{
		if(result[35]==result[36]){result[37]=GAME_TIE;}
		else if(result[38]==1)
		{
			
			result[37]=(result[35]>result[36])? GAME_WISR:GAME_LISR;
			
		}
		else if (Math.abs(Math.round(result[35]-result[36]-snitch*30))<=30)
		{
			result[37]=(result[35]>result[36])? GAME_WISR:GAME_LISR;
		}
		else
		{
			result[37]=(result[35]>result[36])? GAME_WOSR:GAME_LOSR;
		}
	}

	return result;
		
	}
	public static double[] scanQuaffle(String s)
	{
		double[]res = new double[6];
		for(int i=0;i<s.length();i++)
		{
			int sw = Integer.parseInt(s.substring(i,i+1));
			switch(sw)
			{
				case 0:	res[1]++;
						break;
				case 1: res[3]++;
						break;
				case 2: res[5]++;
						break;
				case 3: res[0]++;
						break;
				case 4: res[2]++;
						break;
				case 5: res[4]++;
						break;
				default:	break;
			}
		}
		res[1]+=res[0];
		res[3]+=res[2];
		res[5]+=res[4];
	/*	for(int i=0;i<6;i++)
		{
			System.out.println(res[i]);
		}*/
		return res;
		
	}
	private static double averageIntg(ArrayList<Integer> ish1, ArrayList<Integer> ish2)
	{

		ArrayList<Integer> i1 = sort(ish1);
		ArrayList<Integer> i2 = sort(ish2);
	Integer l = i2.get(i2.size()-1);
	int last = l.intValue();
	double mwol=0;
	for(int i=0;i<i1.size();i++)
	{
	mwol+= i1.get(i).intValue();
	}
	for(int i=0;i<i2.size()-1;i++)
	{
	mwol+=i2.get(i).intValue();
	}
	mwol = mwol/(i1.size()+i2.size()-1);

	if(last<mwol)
	{
		
		return mwol;
	}
	else
	{
	return (mwol*(i1.size()+i2.size()-1)+last)/((i1.size()+i2.size()+1));
	}

	}
	private static double medianIntg(ArrayList<Integer> i1, boolean extra)
	{
		
	
	if(extra&&i1.size()>1)
	{
		double sum = 0;
		for(Integer i: i1)
		{
			sum+= i.intValue();
		}
		sum-=i1.get(i1.size()-1).intValue();
		sum= sum/(i1.size()-1);
		if(i1.get(i1.size()-1).intValue()<sum)
		{i1.remove(i1.size()-1);}
	}
	i1=sort(i1);
	if(i1.size()%2==0)
	{

	return (i1.get(i1.size()/2).doubleValue()+i1.get(i1.size()/2-1).doubleValue())/2;
	}
	else
	{
	return (i1.get((i1.size()-1)/2).doubleValue());
	}
	}
	private static ArrayList<Integer> sort (ArrayList<Integer> i1)
	{

	for(int i=0;i<i1.size();i++)
	{
	int min=i1.get(i).intValue();
	int minIndex =i;
	for(int j=i+1;j<i1.size();j++)
	{
	if(min>i1.get(j).intValue()){
	min = i1.get(j).intValue();
	minIndex=j;
	}
	}
	int temp = i1.get(i); 
	i1.set(i,Integer.valueOf(min));
	i1.set(minIndex,Integer.valueOf(temp));


	}
	return i1;
	}
	public static String toPercent( double d)
	{
		if(d<0){return "N/A";}
		else
	{double percent = d*100;
		percent = (double)Math.round(percent*10);
		percent = percent/10;
		return String.valueOf(percent)+"%";}
		
	}
	public static boolean isDataProcessable(String data)

	{
		boolean result = true;
		try
		{Statistics.process(data);}
		catch(Exception e)
		{
			result = false;
		}
		return result;
	}

}


