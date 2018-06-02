package com.guerzonica.app.picodom;

import com.guerzonica.app.picodom.pages.base.Page;
import java.util.Stack;

//Why not singleton?
public class Navigation<U extends Page> extends Stack<U> {
   private static final long serialVersionUID = 42l;

  @Override
  public U push(U page){
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

  @Override
  public U pop(){ //throws exception if no page left

      U leaving = super.pop();
      leaving.PageWillExit();

      U entering = super.get(super.size() - 1);

      entering.PageWillEnter();

      entering.showPage();


    return leaving;
  }
  // public U push(U page, boolean force){
  //   super.push(page);
  //   page.forceLoad();
  //   return page;
  // }

  public U getActivePage(){
    // System.out.println(super.get(super.size() - 1).getClass());
    // System.out.println(super.size());
    // return
    return super.get(super.size() - 1);

  }

  public void showActivePage(){
    // System.out.println(super.get(super.size() - 1).getClass());
    // System.out.println(super.size());
    // return
     this.getActivePage().showPage();

  }


  public Navigation() {
    super();
  }

}

// package com.guerzonica.app.providers;
//
// import java.lang.reflect.Type;
// import com.guerzonica.app.pages.Page;
// import java.util.Stack;
// import com.guerzonica.app.pages.DashboardPage;
//
// public class PageProvider<U extends Page> extends Stack<U> {
//
//   private static PageProvider pageCtrl = new PageProvider<U>();
//
//   public static PageProvider getInstance(){
//     return PageProvider.pageCtrl;
//   }
//   @Override
//   public U push(U page){
//     super.push(page);
//     //transitions?
//     if(super.empty()){
//
//     } else {
//       // page.
//     }
//     page.setScene();
//     return page;
//   }
//
//   public U getActivePage(){
//     // System.out.println(super.get(super.size() - 1).getClass());
//     // System.out.println(super.size());
//     // return
//     return super.get(super.size() - 1);
//
//   }
//
//
//   private PageProvider(){
//     super();
//   }
//
// }
