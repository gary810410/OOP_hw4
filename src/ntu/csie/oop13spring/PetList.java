package ntu.csie.oop13spring;

import java.util.concurrent.TimeUnit;

public class PetList extends PetBase{
	// just the same as PetBase but be a concrete class
	protected void AIAction()
	{
		setActionNum(0);
		dest = location;
		setMoveStep(4);
	}
}

class PetMudMonster extends PetBase{
	public PetMudMonster()
	{
		MAXhp = 6; setHP(6);
		MAXmp = 4; setMP(3);
		MAXagi = 4;setAGI(3);
		action = new POOAction[3];
		action[0] = new POOAction();
		action[0].skill = new ActionTinyAttack();
		action[1] = new POOAction();
		action[1].skill = new ActionMudSplash();
		action[2] = new POOAction();
		action[2].skill = new SkillList();
	}
	protected String[] getItemsList()
	{
		ListItems = new String[]{"TinyAttack", "MudSplash", "Wait"};
		return ListItems;
	}
	public String getImgPath()
	{
		String imgpath = "mud.png";
		return imgpath;
	}
	protected void AIAction()
	{
		double shortest = 99999;
		CoordinateXY shortestLocation = new CoordinateXY(0,0);
		for(int i=0; i<Pets.length; i++)
		{
			if(location.distance(((PetBase)Pets[i]).getLocation()) < shortest && i != ID && ((PetBase)Pets[i]).checkAlive())
			{
				shortest = location.distance(((PetBase)Pets[i]).getLocation());
				shortestLocation = ((PetBase)Pets[i]).getLocation();
			}
		}
		autoMove(shortestLocation);
		if(movestep == 4)
			return;
		shortest = location.distance(shortestLocation);
		if(shortest <= ((SkillList)(action[1].skill)).range+1 && ((SkillList)(action[1].skill)).getMPcost() <= getMP())
		{
			setActionNum(1);
			if(shortest <= ((SkillList)(action[1].skill)).range)
				dest = shortestLocation;
			else
			{
				int x = shortestLocation.getX(), y = shortestLocation.getY();
				if(location.distance(x+1, y) == ((SkillList)(action[1].skill)).range)
					dest = new CoordinateXY(x+1, y);
				else if(location.distance(x-1, y) == ((SkillList)(action[1].skill)).range)
					dest = new CoordinateXY(x-1, y);
				else if(location.distance(x, y+1) == ((SkillList)(action[1].skill)).range)
					dest = new CoordinateXY(x, y+1);
				else if(location.distance(x, y-1) == ((SkillList)(action[1].skill)).range)
					dest = new CoordinateXY(x, y-1);
			}
		}else if(shortest <= ((SkillList)(action[0].skill)).range)
		{
			setActionNum(0);
			dest = shortestLocation;
		}else
		{
			setActionNum(2);
			dest = location;
		}
		double range = ((SkillList)(currentAction.skill)).getRange();
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				if(location.distance(i, j) <= range)
					matrixbutton[i][j].setBorder(redBorder);
			}
		try{
			TimeUnit.MICROSECONDS.sleep(200000);
		}catch(Exception e){}
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				matrixbutton[i][j].setBorder(defaultBorder);
				if(dest.distance(new CoordinateXY(i,j)) <= ((SkillList)(currentAction.skill)).getSplashRange())
					matrixbutton[i][j].setBorder(redBorder);
			}
		try{
			TimeUnit.MICROSECONDS.sleep(200000);
		}catch(Exception e){}
		setMoveStep(4);
	}
}

class PetSlime extends PetBase{
	public PetSlime()
	{
		MAXhp = 6; setHP(4);
		MAXmp = 4; setMP(2);
		MAXagi = 5;setAGI(4);
		action = new POOAction[3];
		action[0] = new POOAction();
		action[0].skill = new ActionTinyAttack();
		action[1] = new POOAction();
		action[1].skill = new ActionMucus();
		action[2] = new POOAction();
		action[2].skill = new SkillList();;
	}
	protected String[] getItemsList()
	{
		ListItems = new String[]{"TinyAttack", "Mucus", "Wait"};
		return ListItems;
	}
	public String getImgPath()
	{
		String imgpath = "slime.png";
		return imgpath;
	}
	protected void AIAction()
	{
		double shortest = 99999;
		CoordinateXY shortestLocation = new CoordinateXY(0,0);
		int AttackID = 0;
		for(int i=0; i<Pets.length; i++)
		{
			if(location.distance(((PetBase)Pets[i]).getLocation()) < shortest && i != ID && ((PetBase)Pets[i]).checkAlive())
			{
				shortest = location.distance(((PetBase)Pets[i]).getLocation());
				shortestLocation = ((PetBase)Pets[i]).getLocation();
				AttackID = i;
			}
		}
		if(shortest >= 5 && getHP() < getMAXhp())
		{
			resetMatrixButton();
			setMatrixButton();
			try{
				TimeUnit.MICROSECONDS.sleep(1000);
			}catch(Exception e){}
			setActionNum(2);
		}
		else
		{
			autoMove(shortestLocation);
			if(movestep == 4)
				return;
			shortest = location.distance(shortestLocation);
			if(shortest <= ((SkillList)(action[1].skill)).range && ((SkillList)(action[1].skill)).getMPcost() <= getMP() && Pets[AttackID].getAGI() > 2)
			{
				setActionNum(1);
				dest = shortestLocation;
			}else if(shortest <= ((SkillList)(action[0].skill)).range)
			{
				setActionNum(0);
				dest = shortestLocation;
			}else
			{
				setActionNum(2);
				dest = location;
			}
		}
		double range = ((SkillList)(currentAction.skill)).getRange();
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				if(location.distance(i, j) <= range)
					matrixbutton[i][j].setBorder(redBorder);
			}
		try{
			TimeUnit.MICROSECONDS.sleep(200000);
		}catch(Exception e){}
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				matrixbutton[i][j].setBorder(defaultBorder);
				if(dest.distance(new CoordinateXY(i,j)) <= ((SkillList)(currentAction.skill)).getSplashRange())
					matrixbutton[i][j].setBorder(redBorder);
			}
		try{
			TimeUnit.MICROSECONDS.sleep(200000);
		}catch(Exception e){}
		setMoveStep(4);
	}
}
class PetSpider extends PetBase{
	public PetSpider()
	{
		MAXhp = 5; setHP(5);
		MAXmp = 6; setMP(6);
		MAXagi = 6;setAGI(6);
		action = new POOAction[4];
		action[0] = new POOAction();
		action[0].skill = new ActionTinyAttack();
		action[1] = new POOAction();
		action[1].skill = new ActionSilking();
		action[2] = new POOAction();
		action[2].skill = new ActionTrap();
		action[3] = new POOAction();
		action[3].skill = new SkillList();
	}
	protected String[] getItemsList()
	{
		ListItems = new String[]{"TinyAttack", "Silking", "Trap", "Wait"};
		return ListItems;
	}
	public String getImgPath()
	{
		String imgpath = "spider.png";
		return imgpath;
	}
	protected void AIAction()
	{
		double shortest = 99999;
		CoordinateXY shortestLocation = new CoordinateXY(0,0);
		for(int i=0; i<Pets.length; i++)
		{
			if(location.distance(((PetBase)Pets[i]).getLocation()) < shortest && i != ID && ((PetBase)Pets[i]).checkAlive())
			{
				shortest = location.distance(((PetBase)Pets[i]).getLocation());
				shortestLocation = ((PetBase)Pets[i]).getLocation();
			}
		}
		if(shortest >3)
			autoMove(shortestLocation);
		if(movestep == 4)
			return;
		shortest = location.distance(shortestLocation);
		
		int x = location.getX(),y = location.getY();
		if(location.getX() > shortestLocation.getX())
			x = location.getX()-1;
		else if(location.getX() < shortestLocation.getX())
			x = location.getX()+1;
		if(location.getY() > shortestLocation.getY())
			y = location.getY()-1;
		else if(location.getY() < shortestLocation.getY())
			y = location.getY()+1;
		if(shortest > 5)
		{
			setActionNum(3);
			dest = location;
		}
		else if(((SkillList)(action[1].skill)).getMPcost() <= getMP() && layout.checkEffect(new CoordinateXY(x,y), this,  (SkillList)(action[2].skill)))
		{
			setActionNum(1);
			dest = shortestLocation;
		}else if(((SkillList)(action[2].skill)).getMPcost() <= getMP())
		{
			setActionNum(2);
			dest = new CoordinateXY(x,y);
		}else if(((SkillList)(action[0].skill)).getRange() >= shortest)
		{
			setActionNum(0);
			dest = shortestLocation;
		}else
		{
			setActionNum(3);
			dest = location;
		}
		double range = ((SkillList)(currentAction.skill)).getRange();
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				if(location.distance(i, j) <= range)
					matrixbutton[i][j].setBorder(redBorder);
			}
		try{
			TimeUnit.MICROSECONDS.sleep(200000);
		}catch(Exception e){}
		for(int i=0; i<this.width/imgwidth; i++)
			for(int j=0; j<this.height/imgheight; j++)
			{
				matrixbutton[i][j].setBorder(defaultBorder);
				if(dest.distance(new CoordinateXY(i,j)) <= ((SkillList)(currentAction.skill)).getSplashRange())
					matrixbutton[i][j].setBorder(redBorder);
			}
		try{
			TimeUnit.MICROSECONDS.sleep(200000);
		}catch(Exception e){}
		setMoveStep(4);
	}
}