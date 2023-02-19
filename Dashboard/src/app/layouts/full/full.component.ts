import { Component, OnInit, HostListener } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { User } from "../../utils/Models/user.model";
import { DataService } from "../data-service";

//declare var $: any;

@Component({
  selector: "app-full-layout",
  templateUrl: "./full.component.html",
  styleUrls: ["./full.component.scss"],
})
export class FullComponent implements OnInit {
  user: User;
  isSignedin = false;
	//signedinUser: string = '';
	greeting: any[] = [];

  message:string;
  subscription: Subscription;


  constructor(public router: Router, private data: DataService) {}
  public isCollapsed = false;
  public innerWidth: number = 0;
  public defaultSidebar: string = "";
  public showMobileMenu = false;
  public expandLogo = false;
  public sidebartype = "full";
  Logo() {
    this.expandLogo = !this.expandLogo;
  }

  ngOnInit() {
    this.defaultSidebar = this.sidebartype;
    this.handleSidebar();
    // Prendo l'utente salvato appena fatto il login e lo passo al figlio (infatti il figlio chiama @Input della variabile user)
    this.user= JSON.parse(localStorage.getItem("user") || "{}");
   // console.log("Ecco user in full component "+JSON.stringify(this.user))




    this.subscription = this.data.currentMessage.subscribe(message => this.message = message)
    this.data.changeMessage("CIAO DA FULL");
    this.data.setUser(this.user);

    this.subscription = this.data.currentUSer.subscribe(user => this.user = user)
    //console.log("User in FullCompent is " + JSON.stringify(this.user));


  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

change(){
  this.router.navigateByUrl('welcomeHome');
}

  @HostListener("window:resize", ["$event"])
  onResize() {
    this.handleSidebar();
  }

  handleSidebar() {
    this.innerWidth = window.innerWidth;
    if (this.innerWidth < 1170) {
      this.sidebartype = "full";
    } else {
      this.sidebartype = this.defaultSidebar;
    }
  }

  toggleSidebarType() {
    switch (this.sidebartype) {
      case "full":
        this.sidebartype = "mini-sidebar";
        break;

      case "mini-sidebar":
        this.sidebartype = "full";
        break;

      default:
    }
  }
}
