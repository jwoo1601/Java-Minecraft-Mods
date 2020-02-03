package library;

public enum SkinType
{
	DEFAULT, 
	SKIN_OVERLAY_FIRST, 
	SKIN_OVERLAY_SECOND, 
	SKIN_OVERLAY_THIRD, 
	SKIN_OVERLAY_FOURTH,
	SKIN_OVERLAY_FIFTH;
	
	public static SkinType getTypeFromInt(int value)
	{
		 switch (value)
		 {
		 case 0:
			 return SkinType.DEFAULT;
			 
		 case 1:
			 return SkinType.SKIN_OVERLAY_FIRST;
			 
		 case 2:
			 return SkinType.SKIN_OVERLAY_SECOND;
			 
		 case 3:
			 return SkinType.SKIN_OVERLAY_THIRD;
			 
		 case 4:
			 return SkinType.SKIN_OVERLAY_FOURTH;
			 
		 case 5:
			 return SkinType.SKIN_OVERLAY_FIFTH;
			 
			 default:
				 throw new IllegalArgumentException("value is not valid");
		 }
	}
	
	public static int getIntFromType(SkinType type)
	{
		 switch (type)
		 {
		 case DEFAULT:
			 return 0;
			 
		 case SKIN_OVERLAY_FIRST:
			 return 1;
			 
		 case SKIN_OVERLAY_SECOND:
			 return 2;
			 
		 case SKIN_OVERLAY_THIRD:
			 return 3;
			 
		 case SKIN_OVERLAY_FOURTH:
			 return 4;
			 
		 case SKIN_OVERLAY_FIFTH:
			 return 5;
			 
			 default:
				 throw new IllegalArgumentException("type is not valid");
		 }
	}
}
