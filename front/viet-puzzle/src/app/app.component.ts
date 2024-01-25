import { Component, OnInit } from '@angular/core';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { ResultsTableComponent } from './components/results-table/results-table.component';
import { RouterLink, RouterOutlet } from '@angular/router'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  standalone: true,
  imports: [ResultsTableComponent, RouterOutlet, RouterLink],
  providers: [MessageService],
})
export class AppComponent implements OnInit {
  title = 'viet-puzzle';

  constructor(private primengConfig: PrimeNGConfig) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.primengConfig.setTranslation({
      accept: 'Oui',
      reject: 'Non',
  });
  }
}
