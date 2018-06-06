package com.guerzonica.app.picodom;

import com.guerzonica.app.picodom.pages.base.Page;
import java.util.Stack;

/**
* Singleton class responsible to take care about changing pages, user navigations and related events.
* It is a stack.
*
* @author Singh Amarjot
*
* @see com.guerzonica.app.picodom.pages.base.Page
*
*/
public class Navigation extends Stack<Page> {
  private static final long serialVersionUID = 42l;
  private static Navigation instance = null;
  /**
  * Singleton get instance
  * @return The navigation instance
  */
  public static Navigation getNavigation(){
     if(instance == null) instance = new Navigation();
     return instance;
  }

  /**
  * Push and show a new Page.
  *
  * Also set backbutton and other global toolbar things related to the navigations.
  * @param page A new page to show
  * @return the active page
  *
  * @see com.guerzonica.app.picodom.pages.base.Page#PageWillExit() is fired
  * @see com.guerzonica.app.picodom.pages.base.Page#PageWillEnter() is fired
  */
  @Override
  public Page push(Page page){
    if(super.empty()){

    } else {
      super.get(super.size() - 1).PageWillExit();
      page.toolbar.setBackButton();
    }

    page.PageWillEnter();
    super.push(page);
    page.showPage();
    return page;
  }
  /**
  * Pop the active page and show the previous.
  *
  * If the stack is empty it will throw EmptyStackException
  * @return deleted page
  *
  * @see com.guerzonica.app.picodom.pages.base.Page#PageWillExit() is fired
  * @see com.guerzonica.app.picodom.pages.base.Page#PageWillEnter() is fired
  */
  @Override
  public Page pop(){

      Page leaving = super.pop();
      leaving.PageWillExit();

      Page entering = super.get(super.size() - 1);

      entering.PageWillEnter();

      entering.showPage();


    return leaving;
  }
  /*
  * Return active page without deleting it
  * @return active page
  */
  public Page getActivePage(){

    if(super.size() > 0)
      return super.get(super.size() - 1);

    return null;

  }
  /**
  * Util method to show the last pushed page.
  */
  public void showActivePage(){

     this.getActivePage().showPage();

  }

  public Navigation() {
    super();
  }

}
