import {Component, Input} from '@angular/core';
import {FixMessage} from '../../model/fix-message.model';

@Component({
  selector: 'app-fix-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {

  readonly COLUMNS: string[] = ['tag', 'name', 'value', 'description'];
  readonly STYLED_TAGS: number[] = [4, 5, 18, 20, 35, 39, 40, 54, 59, 102, 103, 150];

  @Input()
  selectedMessage: FixMessage;

  getTagStyle(tag: number): boolean {
    return this.STYLED_TAGS.includes(tag);
  }

}
