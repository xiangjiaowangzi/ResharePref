# ResharePref
参考Retrofit实现的一个方便进行SharedPreferences的工具类

## 使用例子
1.Application中初始化，定义自己要的基本变量名SIMPLEPREF_NAME

        ReSharePref.getInstance().init(this , SIMPLEPREF_NAME);

2.定义自己想要保存的接口类

- PrefBody 填入要保存的字段



	@PrefModel(value = "cat")
	
	public interface Cat {
	    String YEAR = "year";
	    String NAME = "name";

        @PrefPut()
        boolean setYear(@PrefBody(YEAR) int year);
        @PrefGet(YEAR)
        int getYear();

	    //多个一并提交
	    @PrefPut()
        boolean setCat(@PrefBody(NAME)String name , @PrefBody(YEAR)int year);
	}

3.在想要的地方进行使用：

	    Cat cat = ReSharePref.getInstance().create(Cat.class);
        cat.setYear(12);
        cat.setName("小猫");
        textView.setText(cat.getName() + " : " + cat.getYear() + "岁");

        //多个提交
        cat.setCat("小猪",8);
