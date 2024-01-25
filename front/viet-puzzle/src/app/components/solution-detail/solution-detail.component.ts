import { Component, OnInit } from '@angular/core';
import { Solution } from '../../Solution';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SolutionService } from '../../services/solution.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { HttpClientModule } from '@angular/common/http';
import { TagModule } from 'primeng/tag';
import { State } from '../../State';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-solution-detail',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    ButtonModule,
    TagModule,
    HttpClientModule,
    ToastModule,
  ],
  providers: [MessageService],
  templateUrl: './solution-detail.component.html',
  styleUrl: './solution-detail.component.scss',
})
export class SolutionDetailComponent implements OnInit {
  solution: Solution | null = null;
  form: any;

  private _sequence: number[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private solutionService: SolutionService,
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.solution = this.route.snapshot.data['solution'];
    this._sequence = this.solution?.sequence || [];
    this.buildForm();
  }

  buildForm(): void {
    this.form = this.fb.group({
      id: [this.solution ? this.solution.id : null],
      sequence: [this.solution ? this.solution.sequence : null],
      state: [this.solution ? this.solution.state : null],
    });
  }

  onSave(): void {
    let call: Observable<Solution> | null = null;

    this.form.controls['sequence'].setValue(this._sequence);

    if (this.form.valid && !this.solution?.id) {
      call = this.solutionService.save(this.form.getRawValue());
    } else if (this.form.valid && this.solution?.id) {
      call = this.solutionService.update(
        this.solution.id,
        this.form.getRawValue()
      );
    }

    if (call)
      call.subscribe((result) => {
        this.solution = result;
        this.messageService.add({
          severity: 'info',
          detail: 'Solution mise à jour.',
        });
        if (result.state === State.CORRECT) {
          this.messageService.add({
            severity: 'success',
            detail: 'La solution est correcte.',
          });
        } else {
          this.messageService.add({
            severity: 'error',
            detail: 'La solution est incorrecte.',
          });
        }
      });
  }

  back(): void {
    this.router.navigate(['']);
  }

  // Getters :
  protected get a(): number {
    return this._sequence[0] || 0;
  }
  protected get b(): number {
    return this._sequence[1] || 0;
  }
  protected get c(): number {
    return this._sequence[2] || 0;
  }
  protected get d(): number {
    return this._sequence[3] || 0;
  }
  protected get e(): number {
    return this._sequence[4] || 0;
  }
  protected get f(): number {
    return this._sequence[5] || 0;
  }
  protected get g(): number {
    return this._sequence[6] || 0;
  }
  protected get h(): number {
    return this._sequence[7] || 0;
  }
  protected get i(): number {
    return this._sequence[8] || 0;
  }

  // Setters :
  protected set a(value: number) {
    this._sequence[0] = value;
  }
  protected set b(value: number) {
    this._sequence[1] = value;
  }
  protected set c(value: number) {
    this._sequence[2] = value;
  }
  protected set d(value: number) {
    this._sequence[3] = value;
  }
  protected set e(value: number) {
    this._sequence[4] = value;
  }
  protected set f(value: number) {
    this._sequence[5] = value;
  }
  protected set g(value: number) {
    this._sequence[6] = value;
  }
  protected set h(value: number) {
    this._sequence[7] = value;
  }
  protected set i(value: number) {
    this._sequence[8] = value;
  }
}
