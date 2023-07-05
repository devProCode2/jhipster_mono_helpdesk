import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChamadoComponent } from '../list/chamado.component';
import { ChamadoDetailComponent } from '../detail/chamado-detail.component';
import { ChamadoUpdateComponent } from '../update/chamado-update.component';
import { ChamadoRoutingResolveService } from './chamado-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const chamadoRoute: Routes = [
  {
    path: '',
    component: ChamadoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChamadoDetailComponent,
    resolve: {
      chamado: ChamadoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChamadoUpdateComponent,
    resolve: {
      chamado: ChamadoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChamadoUpdateComponent,
    resolve: {
      chamado: ChamadoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(chamadoRoute)],
  exports: [RouterModule],
})
export class ChamadoRoutingModule {}
