package ntu.csie.oop13spring;

public class SkillList extends POOSkill{

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
	
	public SkillList()
	{
		MPcost = 0;
		range = 0;
		needAssignPet = false;
		effectSelf = true;
		splashrange = 0;
		name = "wait";
		floorEffectTime = 0;
		floorEffectImgPath = null;
		floorSlowDownSpeed = 0;
		floorSkill = false;
	}
	
	// set normal action
	@Override
	public void act(POOPet pet) {
		// TODO Auto-generated method stub
		if(pet.getMP() < ((PetBase)pet).getMAXmp())
			pet.setMP(pet.getMP()+1);
		if(pet.getAGI() < ((PetBase)pet).getMAXagi() && ((PetBase)pet).getAGIused() == 0)
			pet.setAGI(pet.getAGI()+1);
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
}

class ActionTinyAttack extends SkillList{
	
	public ActionTinyAttack()
	{
		range = 1;
		needAssignPet = true;
		effectSelf = true;
		splashrange = 0;
		name = "tinyAttack";
	}
	public void act(POOPet pet)
	{
		if(pet != null)
		{
			int hp = pet.getHP();
	        if (hp > 0)
	            pet.setHP(hp - 1);
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
		floorEffectTime = 1;
		floorSlowDownSpeed = 1;
		floorEffectImgPath = "brown.png";
	}
	public void act(POOPet pet)
	{
		if(pet!= null)
		{
			int hp = pet.getHP();
			if(hp > 0)
				pet.setHP(hp -1);
		}
	}
	public void FloorEffect(POOPet pet)
	{
		//int agi = pet.getAGI();
		//if(agi > 0)
		//	pet.setAGI(agi -1);
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
		floorEffectTime = 1;
		floorEffectImgPath = "green.png";
	}
	public void act(POOPet pet)
	{
		if(pet!= null)
		{
			int hp = pet.getHP();
			if(hp > 0)
				pet.setHP(hp -1);
			int agi = pet.getAGI();
			if(agi > 0)
				pet.setAGI(agi -2);
		}
	}
}
