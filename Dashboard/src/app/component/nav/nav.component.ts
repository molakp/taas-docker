import { Feed } from './../../dashboard/dashboard-components/feeds/feeds-data';
import { User } from './../../utils/Models/user.model';
import { Feedback } from './../../utils/Models/feedback.model';
import { Component } from '@angular/core';
import {NgbNavChangeEvent} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-ngbd-nav',
  templateUrl: './nav.component.html'
})
export class NgbdnavBasicComponent {

  public localUser: User;
  public localFeedback: Feedback [];
  public count=0;
  currentJustify = 'start';

  active=1;
  activev= "top";

  activeKeep=1;

  activeSelected=1;
  disabled = true;

  
  tabs = [1, 2, 3, 4, 5];
  counter = this.tabs.length + 1;
  activeDynamic=1;

  onNavChange(changeEvent: NgbNavChangeEvent) {
    if (changeEvent.nextId === 4) {
      changeEvent.preventDefault();
    }
  }

  toggleDisabled() {
    this.disabled = !this.disabled;
    if (this.disabled) {
      this.activeSelected = 1;
    }
  }


  close(event: MouseEvent, toRemove: number) {
    this.tabs = this.tabs.filter(id => id !== toRemove);
    event.preventDefault();
    event.stopImmediatePropagation();
  }

  add(event: MouseEvent) {
    this.tabs.push(this.counter++);
    event.preventDefault();
  }

  ngOnInit() {
    // Prendo l'utente salvato appena fatto il login e lo passo al figlio (infatti il figlio chiama @Input della variabile user)
    //this.localFeedback= JSON.parse(localStorage.getItem("feedback") || "{}");
    this.localUser= JSON.parse(localStorage.getItem("user") || "{}");
    this.localFeedback= this.localUser.feedbacks || [];

    for (let i = 0; i < this.localFeedback.length; i++) {
      this.count= this.count+1;
    }
    //console.log("Ecco user in NAV COMPONENT, questi sono i feedbacks "+JSON.stringify(this.localFeedback))
  }


}
