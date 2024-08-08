import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent {
  @Input() width = 24;
  @Input() thickness = 3;
  @Input() color = 'white';

  get loaderColor(){
    return `${this.color} ${this.color} ${this.color} transparent`
  }
}
