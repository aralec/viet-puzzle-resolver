import { Routes } from '@angular/router';
import { ResultsTableComponent } from './components/results-table/results-table.component';
import { SolutionDetailComponent } from './components/solution-detail/solution-detail.component';
import { solutionResolver } from './resolvers/solution.resolver';

export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        component: ResultsTableComponent
    },
    {
        path: ':id',
        component: SolutionDetailComponent,
        resolve: { solution: solutionResolver }
    },
    {
        path: '**',
        redirectTo: ''
    }
];
