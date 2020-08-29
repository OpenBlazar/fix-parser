import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ParserComponent} from './parser.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        pathMatch: 'full',
        redirectTo: '/parser'
      },
      {
        path: 'parser',
        component: ParserComponent
      }
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class ParserRoutingModule {

}
