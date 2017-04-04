package analyser;

import java.awt.event.*;
public class SupportT99
{

	boolean boo = true;

	public String tamilNet99(KeyEvent ke,String text)
	{
		int textLen = text.length();
		int current_key = ke.getKeyCode();
		int current_modifier = ke.getModifiers();

		if(current_key == ke.VK_SHIFT)
			return text;
		else if(current_key == ke.VK_BACK_SPACE && textLen == 0)
		{
			boo = true;
			return "";
		}
		else if(current_key ==ke.VK_BACK_SPACE && textLen > 0 )
		{
			boo = true;
			return (text.substring(0,textLen-1));
		}

		else if((current_key >= 65 && current_key <= 91 )|| (current_key == 93 || current_key == 222 || current_key == 47 || current_key == 59))
		{
			current_key = eng2tam(current_key,current_modifier);

			if(textLen==0)
			{
				if(current_key == 257)
					return ("εχ");
				else
					return (String.valueOf((char)current_key));
			}

			else if(textLen>0)
			{

				if(check(current_key,(int)text.charAt(textLen-1)) && boo)
				{
					boo = false;
					return (replace(text,(int)current_key));
				}
				else
				{
					return (append(text,current_key));
				}
			}
		}
		else
			return (text + String.valueOf((char)current_key));
		return String.valueOf((char)current_key);
	}


	public String append(String present_String, int current_char)
	{
		if(current_char == 257)
			return present_String + String.valueOf('ε')+String.valueOf('χ');
		else
			return present_String + String.valueOf((char)current_char);
	}

	public String replace(String present_String, int current_char)
	{
		return present_String.substring(0,present_String.length()-1) + doProcess(present_String.charAt(present_String.length()-1),current_char);
	}


	public String doProcess(char previous, int current)
	{
	/*
		if previous is ka to na and current is a to au then do process
	*/
		if(((previous >= 232 && previous <= 249) || (previous >= 250 && previous <= 254) && (current >= 220 && current <= 230))|| current==257 )
		{

			if(current == 220)
				return String.valueOf(previous);

			else if(current == 221)
				return String.valueOf(previous) + kaal;

			else if(current == 222)
			{
				if(previous == 236)
					return String.valueOf((char)174);
				return String.valueOf(previous) + chinna_suzhi;
			}
			else if(current == 223)
			{
				if(previous == 236)
					return String.valueOf((char)175);
				return String.valueOf(previous) + periya_suzhi;
			}
			else if(current == 224)
			{
				if(previous == 232)
					return String.valueOf((char)176);
				else if(previous == 233)
					return String.valueOf((char)177);
				else if(previous == 234)
					return String.valueOf((char)178);
				else if(previous == 235)
					return String.valueOf((char)179);
				else if(previous == 236)
					return String.valueOf((char)180);
				else if(previous == 237)
					return String.valueOf((char)181);
				else if(previous == 238)
					return String.valueOf((char)182);
				else if(previous == 239)
					return String.valueOf((char)184);
				else if(previous == 240)
					return String.valueOf((char)185);
				else if(previous == 241)
					return String.valueOf((char)186);
				else if(previous == 242)
					return String.valueOf((char)187);
				else if(previous == 243)
					return String.valueOf((char)188);
				else if(previous == 244)
					return String.valueOf((char)189);
				else if(previous == 245)
					return String.valueOf((char)190);
				else if(previous == 246)
					return String.valueOf((char)191);
				else if(previous == 247)
					return String.valueOf((char)192);
				else if(previous == 248)
					return String.valueOf((char)193);
				else if(previous == 249)
					return String.valueOf((char)194);
				else if(previous >= 250 && previous <= 254)
					return String.valueOf((char)previous)+String.valueOf((char)167);

			}
			else if(current == 225)
			{
				if(previous == 232)
					return String.valueOf((char)195);
				else if(previous == 233)
					return String.valueOf((char)196);
				else if(previous == 234)
					return String.valueOf((char)197);
				else if(previous == 235)
					return String.valueOf((char)198);
				else if(previous == 236)
					return String.valueOf((char)199);
				else if(previous == 237)
					return String.valueOf((char)200);
				else if(previous == 238)
					return String.valueOf((char)201);
				else if(previous == 239)
					return String.valueOf((char)203);
				else if(previous == 240)
					return String.valueOf((char)204);
				else if(previous == 241)
					return String.valueOf((char)205);
				else if(previous == 242)
					return String.valueOf((char)206);
				else if(previous == 243)
					return String.valueOf((char)207);
				else if(previous == 244)
					return String.valueOf((char)214);
				else if(previous == 245)
					return String.valueOf((char)215);
				else if(previous == 246)
					return String.valueOf((char)216);
				else if(previous == 247)
					return String.valueOf((char)217);
				else if(previous == 248)
					return String.valueOf((char)218);
				else if(previous == 249)
					return String.valueOf((char)219);
				else if(previous >= 250 && previous <= 254)
					return String.valueOf((char)previous)+String.valueOf((char)168);


			}
			else if(current == 226)
				return chinna_kombu + String.valueOf(previous);
			else if(current == 227)
				return periya_kombu + String.valueOf(previous);
			else if(current == 228)
				return kai_suzhi + String.valueOf(previous);
			else if(current == 229)
				return chinna_kombu + String.valueOf(previous) + kaal;
			else if(current == 230)
				return periya_kombu + String.valueOf(previous) + kaal;
			else if(current == 257)
			{
				return chinna_kombu + String.valueOf(previous) + String.valueOf((char)247);
			}
		}

		return String.valueOf((char)current);
	}


public boolean check(int current, int previous)
{
	if( ((previous >= 232 && previous <= 249) || (previous >= 250 && previous <= 254)) && ((current >= 220 && current <= 230)||current==257 ||current==231||current==255) )
		return true;
	else
	{
		boo = true;
		return false;
	}
}

	public int eng2tam(int keycode,int modifier)
	{

		if(keycode == 65)
			return 'ά';
		else if(keycode == 66)
			return 'ι';
		else if(keycode == 67)
			return 'ε';
		else if(keycode == 68)
			return 'ΰ';
		else if(keycode == 69)
		{
			if(modifier == 1)
				return 252;
			return 'α';
		}
		else if(keycode == 70)
		{
			if(modifier == 1)
				return 231;
			return 'Ά';
		}
		else if(keycode == 71)
			return 'β';
		else if(keycode == 72)
			return 'θ';
		else if(keycode == 73)
			return 'ω';
		else if(keycode == 74)
			return 'π';
		else if(keycode == 75)
			return 'ρ';
		else if(keycode == 76)
			return 'ξ';
		else if(keycode == 77)
			return 'σ';
		else if(keycode == 78)
			return 'τ';
		else if(keycode == 79)
			return 'μ';
		else if(keycode == 80)
			return 'ν';
		else if(keycode == 81)
		{
			if(modifier == 1)
				return 250;
			return 'έ';
		}
		else if(keycode == 82)
		{
			if(modifier == 1)
				return 253;
			return 'δ';
		}
		else if(keycode == 83)
			return 'ή';
		else if(keycode == 84)
		{
			if(modifier == 1)
				return 254;
			return 'γ';
		}
		else if(keycode == 85)
			return 'ψ';
		else if(keycode == 86)
			return 'υ';
		else if(keycode == 87)
		{
			if(modifier == 1)
				return 251;
			return 'ί';
		}
		else if(keycode == 88)
			return 'ζ';
		else if(keycode == 89)
		{
			if(modifier == 1)
				return 255;
			return 'χ';
		}

// this is for au
		else if(keycode == 90)
			return 257;

		else if(keycode == 91)
			return 'κ';
		else if(keycode == 93)
			return 'λ';
		else if(keycode == 222)
			return (char)242;
		else if (keycode == 59)
			return 'ο';
		else if(keycode == 47)
			return 'φ';

		return (char) keycode;
	}

String kaal = "£";
String chinna_suzhi = "¤";
String periya_suzhi = "¦";
String chinna_kombu = "ª";
String periya_kombu = "«";
String kai_suzhi = "¬";
}
