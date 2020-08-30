import {NgModule} from '@angular/core';
import {ParserComponent} from './parser.component';
import {ParserRoutingModule} from './parser.routing.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {ParserService} from './parser.service';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MessageComponent} from './message/message.component';
import {CommonModule} from '@angular/common';
import {MessagesComponent} from './messages/messages.component';
import {DictionaryService} from './dictionary.service';
import {MessageComparatorComponent} from './compare/message-comparator.component';
import {ComparatorService} from './compare/comparator.service';
import {MatExpansionModule} from '@angular/material/expansion';
import {MessageSelectorComponent} from './message-selector/message-selector.component';

@NgModule({
  imports: [
    ParserRoutingModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    TranslateModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    CommonModule,
    FormsModule,
    MatExpansionModule
  ],
  declarations: [
    ParserComponent,
    MessageComponent,
    MessagesComponent,
    MessageComparatorComponent,
    MessageSelectorComponent
  ],
  providers: [
    ParserService,
    DictionaryService,
    ComparatorService
  ]
})
export class ParserModule {

}
