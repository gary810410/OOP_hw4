package ntu.csie.oop13spring;

public class PetList extends PetBase{
	// just the same as PetBase but be a concrete class
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
}

class PetSlime extends PetBase{
	public PetSlime()
	{
		MAXhp = 5; setHP(5);
		MAXmp = 4; setMP(2);
		MAXagi = 5;setAGI(4);
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