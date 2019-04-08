package deeplearningGroup1;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.TestTools;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.AbstractCompoundRule;
import org.languagetool.rules.CommaWhitespaceRule;
import org.languagetool.rules.GenericUnpairedBracketsRule;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;



/**
 * @author Antonino Abeshi
 * This checks for grammatical errors. 
 */
public class GrammarCheck {
	
	StudentGUI student = new StudentGUI(null);
	
	public static void main(String[] args) throws IOException {
		   
			JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
	

			/*for (Rule rule : langTool.getAllRules()) {
				  if (!rule.isDictionaryBasedSpellingRule()) {
					  System.out.println(rule.getId());
				  }
			}*/
			
			
			
			System.out.println(langTool.getDisabledRules());
			
			//langTool.disableRule("COMMA_PARENTHESIS_WHITESPACE");
			
			int count = 0;
			
			List<RuleMatch> matches = langTool.check("Dear local newspaper, I think effects computers have on people are great learning skills/affects because they give us time to chat with friends/new people, helps us learn about the globe(astronomy) and keeps us out of troble! Thing about! Dont you think so? How would you feel if your teenager is always on the phone with friends! Do you ever time to chat with your friends or buisness partner about things. Well now - there's a new way to chat the computer, theirs plenty of sites on the internet to do so: @ORGANIZATION1, @ORGANIZATION2, @CAPS1, facebook, myspace ect. Just think now while your setting up meeting with your boss on the computer, your teenager is having fun on the phone not rushing to get off cause you want to use it. How did you learn about other countrys/states outside of yours? Well I have by computer/internet, it's a new way to learn about what going on in our time! You might think your child spends a lot of time on the computer, but ask them so question about the economy, sea floor spreading or even about the @DATE1's you'll be surprise at how much he/she knows. Believe it or not the computer is much interesting then in class all day reading out of books. If your child is home on your computer or at a local library, it's better than being out with friends being fresh, or being perpressured to doing something they know isnt right. You might not know where your child is, @CAPS2 forbidde in a hospital bed because of a drive-by. Rather than your child on the computer learning, chatting or just playing games, safe and sound in your home or community place. Now I hope you have reached a point to understand and agree with me, because computers can have great effects on you or child because it gives us time to chat with friends/new people, helps us learn about the globe and believe or not keeps us out of troble. Thank you for listening.");
			for (RuleMatch match : matches) {	
			   count++;
			  System.out.println("Potential typo at characters " + match.getFromPos() + "-" + match.getToPos() + ": " + match.getMessage());
			  System.out.println("Suggested correction(s): " + match.getSuggestedReplacements());
			  	
			 
			}
			   System.out.println("There are " + count + " mistakes");
			  
			}
			
			
		}

	


