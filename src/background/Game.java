package background;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import background.Interval.Position;
import javafx.application.Platform;
import taiko.*;

public class Game 
{
	private FXMLDocumentController controller;

	private String song;
	private long startTime;
	private List<Interval> intervalsL=new ArrayList<>();
	private List<Interval> intervalsR=new ArrayList<>();
	private List<Long> spawnsL=new ArrayList<>();
	private List<Long> spawnsR=new ArrayList<>();
	private List<Guide> guides=new ArrayList<>();
	private Map<Long,Guide> guidesMap=new HashMap<>();
	private int fails;
	private boolean end;
	private boolean win;
	private long score;

	//INITIALIZATION
	public Game(FXMLDocumentController c,String song) 
	{
		controller=c;
		this.song=song;
		try(BufferedReader bf=new BufferedReader(new FileReader(song+".txt"))) 
		{
			String s;
			while((s=bf.readLine())!=null)
				new Interval(this,s);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	//GAME
	public void start()
	{
		end=false;
		win=false;
		fails=0;
		score=0;
		boolean endL=false,endR=false;
		Clip clip=null;
		try 
		{
			Thread.sleep(1000);
			File soundfile=new File(song+".wav");
			clip = AudioSystem.getClip();
    		AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundfile);
		    clip.open(inputStream);
		    clip.start();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		setStartTime(System.currentTimeMillis());
		int currentL=0,currentR=0;
		try 
		{
			while(!end)
			{
				long time=System.currentTimeMillis()-getStartTime();
				int i;
				if((!endL)&&time>spawnsL.get(currentL))
				{
					Guide g=new Guide(this,controller,Position.left);
					guides.add(g);
					guidesMap.put(spawnsL.get(currentL),g);
					currentL++;
					if(currentL==spawnsL.size())
						endL=true;
				}
				if((!endR)&&time>spawnsR.get(currentR))
				{
					Guide g=new Guide(this,controller,Position.right);
					guides.add(g);
					guidesMap.put(spawnsR.get(currentR),g);
					currentR++;
					if(currentR==spawnsR.size())
						endR=true;
				}
				for(i=guides.size()-1;i>=0;i--)
				{
					if(!(guides.get(i).isVisible()))
						break;
					guides.get(i).move();
				}
				end=checkFails();
				if(endL&&endR&&(!guides.get(guides.size()-1).isVisible()))
				{
					end=true;
					win=true;
				}
				Thread.sleep(27);
			}
			clip.close();
			if(win==true)
				Platform.runLater(()->controller.goRnk(song,score));
			else
			{
				for(Guide g:guides)
					g.destroy();
				Platform.runLater(()->controller.goReplay(song));
			}
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	//GAME FUNCTIONS
	public void incrementFails() 
	{
		fails++;
	}
	private boolean checkFails()
	{
		if(fails>=5)
			return true;
		return false;
	}
	public void incrementScore(long score)
	{
		this.score+=score;
	}
	public void binarySearchLeft(long time) 
	{
		int l=0;
		int r=intervalsL.size()-1;
		if(!binarySearchR(intervalsL,time,l,r))
			Platform.runLater(()->controller.blueBad());;
	}
	public void binarySearchRight(long time) 
	{
		int l=0;
		int r=intervalsR.size()-1;
		if(!binarySearchR(intervalsR,time,l,r))
			Platform.runLater(()->controller.redBad());
	}
	private boolean binarySearchR(List<Interval> intervals,long time,int l,int r) 
	{
		if(l>r)
		{
			incrementFails();
			return false;
		}
		int m=(l+r)/2;
		int flag=intervals.get(m).contains(time);
		if(flag==0)
		{
			int good=intervals.get(m).containsG(time);
			if(good==0)
			{
				Guide g=guidesMap.get(intervals.get(m).getLimitL()-950);
				g.setChecked(true);
				int perfect=intervals.get(m).containsP(time);
				if(perfect==0)
					g.setPerfect(true);
			}
			else if((m>0)&&(good<0)&&(intervals.get(m-1).containsG(time)==0))
			{
				Guide g=guidesMap.get(intervals.get(m-1).getLimitL()-950);
				g.setChecked(true);
				int perfect=intervals.get(m-1).containsP(time);
				if(perfect==0)
					g.setPerfect(true);
			}
			else if(((m+1)<(intervals.size()))&&(good>0)&&(intervals.get(m+1).containsG(time)==0))
			{
				Guide g=guidesMap.get(intervals.get(m+1).getLimitL()-950);
				g.setChecked(true);
				int perfect=intervals.get(m+1).containsP(time);
				if(perfect==0)
					g.setPerfect(true);
			}
			return true;
		}
		else if(flag>0)
			return binarySearchR(intervals,time,m+1,r);
		else
			return binarySearchR(intervals,time,l,m-1);
	}
	public void addIntervalL(Interval interval) 
	{
		intervalsL.add(interval);
	}
	public void addIntervalR(Interval interval) 
	{
		intervalsR.add(interval);
	}
	public void addSpawnL(Long time)
	{
		spawnsL.add(time);
	}
	public void addSpawnR(Long time)
	{
		spawnsR.add(time);
	}
	
	//GETTERS AND SETTERS
	public long getStartTime() 
	{
		return startTime;
	}
	public void setStartTime(long startTime) 
	{
		this.startTime = startTime;
	}
	public FXMLDocumentController getController()
	{
		return controller;
	}
}
