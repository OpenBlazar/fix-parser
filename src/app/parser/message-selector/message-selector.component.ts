import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FixMessage} from '../../model/fix-message.model';

@Component({
  selector: 'app-message-selector',
  templateUrl: './message-selector.component.html',
  styleUrls: ['./message-selector.component.scss']
})
export class MessageSelectorComponent {

  @Input()
  messages: FixMessage[];

  @Input()
  selectedMessage: FixMessage;

  @Output()
  onMessageSelected: EventEmitter<FixMessage> = new EventEmitter<FixMessage>();

  compareObjects(o1: FixMessage, o2: FixMessage): boolean {
    return o1.messageId === o2.messageId;
  }

}
