package pw.evan.PlayerFreeze.util;

public class TimeUtil
{
    public static long parseTimeString(String timeString)
    {
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int index = 0;
        boolean parsingNumber = false;
        StringBuilder number = new StringBuilder();
        for(char current : timeString.toCharArray())
        {
            if(isNumber(current))
            {
                if(!parsingNumber)
                {
                    parsingNumber = true;
                    number = new StringBuilder();
                }
                number.append(current);
            }
            else if(isValidLetter(current))
            {
                if(parsingNumber)
                {
                    switch(current)
                    {
                        case 'd':
                            if(days>0)
                            {
                                throw new IllegalArgumentException("Duplicate value for days!");
                            }
                            else
                            {
                                days = Integer.parseInt(number.toString());
                                parsingNumber = false;
                            }
                            break;
                        case 'h':
                            if(hours>0)
                            {
                                throw new IllegalArgumentException("Duplicate value for hours!");
                            }
                            else
                            {
                                hours = Integer.parseInt(number.toString());
                                parsingNumber = false;
                            }
                            break;
                        case 'm':
                            if(minutes>0)
                            {
                                throw new IllegalArgumentException("Duplicate value for minutes!");
                            }
                            else
                            {
                                minutes = Integer.parseInt(number.toString());
                                parsingNumber = false;
                            }
                            break;
                        case 's':
                            if(seconds>0)
                            {
                                throw new IllegalArgumentException("Duplicate value for seconds!");
                            }
                            else
                            {
                                seconds = Integer.parseInt(number.toString());
                                parsingNumber = false;
                            }
                            break;
                    }
                }
                else
                {
                    throw new IllegalArgumentException("Unexpected character '"+current+"' in time string at index "+index+"!");
                }
            }
            else
            {
                throw new IllegalArgumentException("Invalid character '"+current+"' in time string at index "+index+"!");
            }
            index++;
        }
        //parsing complete! let's turn it into a return value
        seconds += (minutes * 60);
        seconds += (hours * 3600);
        seconds += (days * 86400);
        return seconds;
    }

    private static boolean isNumber(char testChar)
    {
        switch(testChar)
        {
            case '0':case'1':case'2':case'3':case'4':case'5':case'6':case'7':case'8':case'9':
                return true;
            default:
                return false;
        }
    }

    private static boolean isValidLetter(char testChar)
    {
        String testString = String.valueOf(testChar);
        testChar = testString.toLowerCase().charAt(0);
        switch(testChar)
        {
            case 'd':case'h':case'm':case's':
                return true;
            default:
                return false;
        }
    }

    public static long time(){
        return System.currentTimeMillis()/1000;
    }

    public static String makeFormatString(long seconds)
    {
        long days = seconds / 86400;
        seconds = seconds % 86400;
        long hours = seconds / 3600;
        seconds = seconds % 3600;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        StringBuilder formatted = new StringBuilder();
        if(days>0)
        {
            formatted.append(days);
            formatted.append(" days");
            if(hours>0)
            {
                formatted.append(", ");
            }
            else
            {
                formatted.append(" ");
            }
        }
        if(hours>0)
        {
            formatted.append(hours);
            formatted.append(" hours");
            if(minutes>0)
            {
                formatted.append(", ");
            }
            else
            {
                formatted.append(" ");
            }
        }
        if(minutes>0)
        {
            formatted.append(minutes);
            formatted.append(" minutes");
            if(seconds>0)
            {
                formatted.append(", ");
            }
            else
            {
                formatted.append(" ");
            }
        }
        if (seconds > 0)
        {
            formatted.append(seconds);
            formatted.append(" seconds");
        }
        return formatted.toString();
    }
}
