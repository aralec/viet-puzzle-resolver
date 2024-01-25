import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Solution } from "../Solution";
import { SolutionService } from "../services/solution.service";
import { inject } from "@angular/core";
import { Observable } from "rxjs";

export const solutionResolver: ResolveFn<Solution> = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
    solutionService: SolutionService = inject(SolutionService)
): Observable<Solution> => solutionService.read(route.paramMap.get('id'));
