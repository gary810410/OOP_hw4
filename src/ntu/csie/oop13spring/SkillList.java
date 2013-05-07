package ntu.csie.oop13spring;

import javax.swing.JLayeredPane;

public class SkillList extends POOSkill{

	protected String AttackImage;
	protected double range;
	protected boolean needAssignPet;
	protected boolean effectSelf;
	protected double splashrange;
	protected String name;
	protected int floorEffectTime;
	protected String floorEffectImgPath;
	protected int MPcost;
	protected int floorSlowDownSpeed;
	protected boolean floorSkill;
	protected CoordinateXY location;
	protected int ImgSize;
	protected boolean showOnce;
	protected boolean floorVisible;
	
	public SkillList()
	{
		MPcost = 0;
		range = 0;
		needAssignPet = false;
		effectSelf = true;
		splashrange = 0;
		name = "wait";
		AttackImage = "sleep.png";
		floorEffectTime = 0;
		floorEffectImgPath = null;
		floorSlowDownSpeed = 0;
		floorSkill = false;
		showOnce = true;
		ImgSize = 40;
		floorVisible = true;
		location = new CoordinateXY(1,-1);
	}
	
	// set normal action
	@Override
	public void act(POOPet pet) {
		// TODO Auto-generated method stub
		if(pet.getMP() < ((PetBase)pet).getMAXmp())
			pet.setMP(pet.getMP()+1);
		if(pet.getAGI() < ((PetBase)pet).getMAXagi() && ((PetBase)pet).getAGIused() == 0)
			pet.setAGI(pet.getAGI()+1);
		checkAlive(pet);
	}
	
	// set floor action
	public void FloorEffect(POOPet pet)
	{
		
	}
	public String GetFloorImg()
	{
		return floorEffectImgPath;
	}
	
	// retrieval function
	public String getAttackImg()
	{
		return AttackImage;
	}
	public boolean ShowOnce()
	{
		return showOnce;
	}
	public int getImgSize()
	{
		return ImgSize;
	}
	public CoordinateXY getLocation()
	{
		return location;
	}
	public boolean needAssignPet()
	{
		return needAssignPet;
	}
	public boolean effectSelf()
	{
		return effectSelf;
	}
	public double getRange()
	{
		return range;
	}
	public double getSplashRange()
	{
		return splashrange;
	}
	public String getName()
	{
		return name;
	}
	public int getFloorEffectTime()
	{
		return floorEffectTime;
	}
	public int getMPcost()
	{
		return MPcost;
	}
	public int getSlowDown()
	{
		return floorSlowDownSpeed;
	}
	public boolean isFloorSkill()
	{
		return floorSkill;
	}
	public boolean floorVisible()
	{
		return floorVisible;
	}
	public void checkAlive(POOPet pet)
	{
		int hp = pet.getHP();
		if(hp <= 0)
		{
			TransparentIcon TIcon = new TransparentIcon(((PetBase)pet).getDeathImg());
			JLayeredPane background = ((PetBase)pet).getBackGround();
			background.setLayer(((PetBase)pet).getitself(), JLayeredPane.MODAL_LAYER);
			((PetBase)pet).getitself().setIcon(TIcon.getIcon());
			((PetBase)pet).setRIP();
		}
	}
}

class ActionTinyAttack extends SkillList{
	
	public ActionTinyAttack()
	{
		range = 1;
		needAssignPet = true;
		effectSelf = true;
		splashrange = 0;
		name = "tinyAttack";
		AttackImage = "TinyAttack.png";
		location = new CoordinateXY(0,0);
	}
	public void act(POOPet pet)
	{
		if(pet != null)
		{
			int hp = pet.getHP();
	        if (hp > 0)
	            pet.setHP(hp - 1);
	        checkAlive(pet);
		}
	}
}

class ActionMudSplash extends SkillList{
	
	public ActionMudSplash()
	{
		floorSkill = true;
		MPcost = 2;
		range = 2;
		needAssignPet = true;
		effectSelf = false;
		splashrange = 1;
		name = "Mud";
		AttackImage = "MudBubble.png";
		floorEffectTime = 1;
		floorSlowDownSpeed = 1;
		floorEffectImgPath = "brown.png";
		ImgSize = 120;
		location = new CoordinateXY(0,-1);
	}
	public void act(POOPet pet)
	{
		if(pet!= null)
		{
			int hp = pet.getHP();
			if(hp >= 2)
				pet.setHP(hp -2);
			else if(hp < 2)
				pet.setHP(0);
			int agi = pet.getAGI();
			if(agi > 0)
				pet.setAGI(agi -1);
			checkAlive(pet);
		}
	}
	public void FloorEffect(POOPet pet)
	{
	}
}

class ActionFongi extends SkillList{
	
	public ActionFongi()
	{
		MPcost = 1;
		range = 3;
		needAssignPet = true;
		effectSelf = true;
		splashrange = 0;
		name = "Fongi";
		AttackImage = "green.png";
		location = new CoordinateXY(0,0);
	}
	public void act(POOPet pet)
	{
		if(pet!= null)
		{
			int hp = pet.getHP();
			if(hp > 0)
				pet.setHP(hp -1);
			int agi = pet.getAGI();
			if(agi >= 2)
				pet.setAGI(agi-2);
			else if(agi < 2)
				pet.setAGI(0);
			checkAlive(pet);
		}
	}
}
class ActionSilking extends SkillList
{
	public ActionSilking()
	{
		MPcost = 1;
		range = 5;
		needAssignPet = true;
		effectSelf = false;
		splashrange = 0;
		name = "Silking";
		AttackImage = "Silking.png";
		floorEffectTime = 0;
		floorEffectImgPath = null;
		floorSlowDownSpeed = 0;
		floorSkill = false;
		showOnce = true;
		ImgSize = 40;
		location = new CoordinateXY(0,0);
	}
	public void act(POOPet pet)
	{
		if(pet!= null)
		{
			int mp = pet.getMP();
			if(mp < 2)
				pet.setMP(0);
			else if(mp >= 2)
				pet.setMP(mp-2);
			int agi = pet.getAGI();
			if(agi < 2)
				pet.setAGI(0);
			else if(agi >= 2)
				pet.setAGI(agi-2);
		}
		POOPet actPet = ((PetBase)pet).getCurrentPet();
		CoordinateXY locationA = ((PetBase)pet).getLocation();
		CoordinateXY locationB = ((PetBase)actPet).getLocation();
		int x = locationB.getX(),y = locationB.getY();
		if(locationA.getX() > locationB.getX())
			x = locationB.getX()+1;
		else if(locationA.getX() < locationB.getX())
			x = locationB.getX()-1;
		if(locationA.getY() > locationB.getY())
			y = locationB.getY()+1;
		else if(locationA.getY() < locationB.getY())
			y = locationB.getY()-1;
		((PetBase)pet).setLocation(x,y);
		(((PetBase)pet).getitself()).setBounds(x*40, y*40, 40, 40);
		(((PetBase)pet).getLayout()).FloorEffect(pet, new CoordinateXY(x,y));
	}
}
class ActionTrap extends SkillList
{
	public ActionTrap()
	{
		MPcost = 2;
		range = 2;
		needAssignPet = true;
		effectSelf = false;
		splashrange = 0;
		name = "trap";
		AttackImage = "trap.png";
		floorEffectTime = -1;
		floorEffectImgPath = "trap.png";
		floorSlowDownSpeed = 0;
		floorSkill = true;
		showOnce = true;
		ImgSize = 40;
		floorVisible = false;
		location = new CoordinateXY(0,0);
	}
	public void act(POOPet pet) {
		// TODO Auto-generated method stub
		
	}
	public void FloorEffect(POOPet pet)
	{
		(((PetBase)pet).getLayout()).setFloorEffect(((PetBase)pet).getLocation(), 2, 3, true);
		int hp = pet.getHP();
		if(hp >= 2)
			pet.setHP(hp -2);
		else if(hp < 2)
			pet.setHP(0);
		((PetBase)pet).setMoveStep(4);
		checkAlive(pet);
	}
}