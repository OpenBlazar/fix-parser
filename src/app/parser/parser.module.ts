import {NgModule} from "@angular/core";
import {ParserComponent} from "./parser.component";
import {ParserRoutingModule} from "./parser.routing.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";

@NgModule({
  imports: [
    ParserRoutingModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    TranslateModule,
    MatButtonModule,
    MatInputModule
  ],
  declarations: [
    ParserComponent
  ]
})
export class ParserModule {

}
