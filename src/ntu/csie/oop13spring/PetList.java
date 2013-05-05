package ntu.csie.oop13spring;

public class PetList extends PetBase{
	// just the same as PetBase but be a concrete class
}

class PetMudMonster extends PetBase{
	public PetMudMonster()
	{
		setHP(6);
		setMP(2);
		setAGI(3);
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
}

class PetSlime extends PetBase{
	public PetSlime()
	{
		setHP(5);
		setMP(1);
		setAGI(4);
		action = new POOAction[3];
		action[0] = new POOAction();
		action[0].skill = new ActionTinyAttack();
		action[1] = new POOAction();
		action[1].skill = new ActionFongi();
		action[2] = new POOAction();
		action[2].skill = new SkillList();
	}
	protected String[] getItemsList()
	{
		ListItems = new String[]{"TinyAttack", "Fongi", "Wait"};
		return ListItems;
	}
	public String getImgPath()
	{
		String imgpath = "slime.png";
		return imgpath;
	}
}