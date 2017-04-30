package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class AnalyticsListener
 *
 */
@WebListener
public class AnalyticsListener implements HttpSessionAttributeListener {

    /**
     * Default constructor. 
     */
    public AnalyticsListener() {
        // TODO Auto-generated constructor stub
    }
	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
//    	String event = arg0.getName();
//    	String title = request.getParameter("book");
//
//		int indexBy = title.indexOf("by");
//
//		String bookname = title.substring(0, indexBy - 1);
//		int indexDash = title.indexOf("-");
//		String author = title.substring(indexBy + 3, indexDash - 1);
//		System.out.println(author);
//		
//		this.database.createVisitEvent(bookname, author, "VIEW");
    }

	
}
