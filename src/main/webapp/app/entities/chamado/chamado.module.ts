import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChamadoComponent } from './list/chamado.component';
import { ChamadoDetailComponent } from './detail/chamado-detail.component';
import { ChamadoUpdateComponent } from './update/chamado-update.component';
import { ChamadoDeleteDialogComponent } from './delete/chamado-delete-dialog.component';
import { ChamadoRoutingModule } from './route/chamado-routing.module';

@NgModule({
  imports: [SharedModule, ChamadoRoutingModule],
  declarations: [ChamadoComponent, ChamadoDetailComponent, ChamadoUpdateComponent, ChamadoDeleteDialogComponent],
})
export class ChamadoModule {}
