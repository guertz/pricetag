
package com.guerzonica.app.providers;

import com.guerzonica.app.pages.Page;
import java.util.Stack;
//Singleton or use global static Generated class?
public class PageProvider<U extends Page> extends Stack<U> {
   private static final long serialVersionUID = 42l;

  @Override
  public U push(U page){

    if(super.empty()){

    } else {
      // this.getActivePage()
    }
    super.push(page);
    //transitions?

    page.setScene();
    return page;
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
     this.getActivePage().setScene();

  }


  public PageProvider(){
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
