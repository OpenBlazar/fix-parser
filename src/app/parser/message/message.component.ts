import {Component, Input} from '@angular/core';
import {isEmpty} from 'lodash';
import {FixMessage} from '../../model/fix-message.model';
import {FixMessageView} from '../messages/messages.component';
import {FixPair} from '../../model/fix-pair.model';

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

  @Input()
  highlightedRows: number[] = [];

  getTagStyle(tag: number): boolean {
    return this.STYLED_TAGS.includes(tag);
  }

  getRowHighlight(field: FixPair): string {
    return (this.highlightedRows || []).includes(field.fixField?.tag) ? 'selectedRow' : '';
  }

}
