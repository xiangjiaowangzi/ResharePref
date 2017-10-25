# ResharePref
参考Retrofit实现的一个方便进行SharedPreferences的工具类

## 使用例子
1.Application中初始化

        ReSharePref.getInstance().init(this , SIMPLEPREF_NAME);
        
2.定义自己想要保存的接口类

	@PrefModel(value = "cat")
	public interface Cat {
	    String YEAR = "year";
	    String NAME = "name";
	
	    @PrefPut(YEAR)
	    boolean setYear(int year);
	    @PrefGet(YEAR)
	    int getYear();
	
	    @PrefPut(NAME)
	    boolean setName(String name);
	    @PrefGet(NAME)
	    String getName();
	}

3.在想要的地方进行使用：

	    Cat cat = ReSharePref.getInstance().create(Cat.class);
        cat.setYear(12);
        cat.setName("小猫");
        textView.setText(cat.getName() + " : " + cat.getYear() + "岁");
