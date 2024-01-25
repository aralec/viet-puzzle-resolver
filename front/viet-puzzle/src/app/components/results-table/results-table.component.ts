import { Component, OnInit } from '@angular/core';
import { SolutionService } from '../../services/solution.service';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { HttpClientModule } from '@angular/common/http';
import { concatMap, tap } from 'rxjs';
import { Solution } from '../../Solution';
import { Router } from '@angular/router';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';

@Component({
  selector: 'app-results-table',
  templateUrl: './results-table.component.html',
  styleUrl: './results-table.component.scss',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    HttpClientModule,
    TagModule,
    TooltipModule,
    ToastModule,
    ConfirmPopupModule
  ],
  providers: [MessageService, ConfirmationService],
})
export class ResultsTableComponent implements OnInit {
  generationTime: number = 0;
  solutions: Solution[] = [];

  constructor(
    private solutionService: SolutionService,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    // On load les solutions pré-existantes, s'il y en a
    this.solutionService
      .search()
      .subscribe((solutions) => (this.solutions = solutions));
  }

  generateSolutions(): void {
    this.solutionService
      .generateSolutions()
      .pipe(
        tap((time) => (this.generationTime = time)),
        concatMap(() => this.solutionService.search())
      )
      .subscribe((solutions) => {
        this.solutions = solutions;
        this.messageService.add({
          severity: 'info',
          detail: `${solutions.length} solutions générées.`,
        });
      });
  }

  onEdit(id: string): void {
    this.router.navigate(['/', id]);
  }

  onDelete(event: Event, id: number): void {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Cette action est irréversible.',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this._onDelete(id);
      },
      reject: () => {},
    });
  }

  onDeleteAll(event: Event): void {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Êtes vous certain de vouloir supprimer l\'ensemble des solutions ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon:"none",
      rejectIcon:"none",
      rejectButtonStyleClass:"p-button-text",
      accept: () => {
         this._onDeleteAll();
      },
      reject: () => {}
  });
  }

  formatSolution(id: number): string {
    // "jolie" est une question de point de vue
    const s: Solution | undefined = this.solutions.find((s) => s.id === id);
    return s ? this._getEquation(s) : '';
  }

  private _onDelete(id: number): void {
    this.solutionService.delete(id).subscribe(() => {
      this.solutions = this.solutions.filter((s) => s.id !== id);
      this.messageService.add({
        severity: 'error',
        detail: 'Solution supprimée.',
      });
    });
  }

  private _onDeleteAll(): void {
    this.solutionService.deleteAll().subscribe(() => {
      this.solutions = []
      this.messageService.add({
        severity: 'info',
        detail: 'Base de données nettoyée.',
      });
    });
  }

  private _getEquation(solution: Solution): string {
    const sequence = solution.sequence;
    return `${sequence[0]} + (13 * ${sequence[1]}) / ${sequence[2]} + ${sequence[3]} + (12 * ${sequence[4]}) - ${sequence[5]} - (11 + ${sequence[6]} *  ${sequence[7]}) / ${sequence[8]} - 10`;
  }
}
