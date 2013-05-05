package ntu.csie.oop13spring;

public class SkillList extends POOSkill{

	protected double range;
	protected boolean needAssignPet;
	protected boolean effectSelf;
	protected double splashrange;
	protected String name;
	
	public SkillList()
	{
		range = 0;
		needAssignPet = false;
		effectSelf = true;
		splashrange = 0;
		name = "wait";
	}
	@Override
	public void act(POOPet pet) {
		// TODO Auto-generated method stub
		;
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
		range = 2;
		needAssignPet = true;
		effectSelf = true;
		splashrange = 1;
		name = "Mud";
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
				pet.setAGI(agi -1);
		}
	}
}

class ActionFongi extends SkillList{
	
	public ActionFongi()
	{
		range = 3;
		needAssignPet = true;
		effectSelf = true;
		splashrange = 0;
		name = "Fongi";
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
