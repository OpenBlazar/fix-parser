import {Component, EventEmitter, Input, Output} from '@angular/core';
import {isNil} from 'lodash';
import {FixPair} from '../../model/fix-pair.model';
import {FixMessage} from '../../model/fix-message.model';

export interface FixMessageView extends FixMessage {
  sender: FixPair;
  receiver: FixPair;
  sendingTime: FixPair;
  ordStatus: FixPair;
}

@Component({
  selector: 'app-fix-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent {

  readonly ORD_STATUS_STYLE = {
    0: 'fieldcolumn-green',
    1: 'fieldcolumn-purple-light',
    2: 'fieldcolumn-purple-hard',
    4: 'fieldcolumn-red',
    5: 'fieldcolumn-dust',
    8: 'fieldcolumn-red',
  };
  readonly ORD_TYPE_STYLE = {
    9: 'fieldcolumn-red',
    F: 'fieldcolumn-orange',
    G: 'fieldcolumn-orange',
    8: 'fieldcolumn-blue',
    D: 'fieldcolumn-green'
  };
  readonly COLUMNS: string[] = ['id', 'sender', 'receiver', 'sendingTime', 'msgType', 'summary'];
  fixMessages: FixMessageView[];

  @Input()
  set messages(messages: FixMessage[]) {
    this.fixMessages = messages.map(message => (
      {
        messageId: message.messageId,
        version: message.version,
        messageFields: message.messageFields,
        messageType: message.messageType,
        sender: this.findField(message.messageFields, 49),
        receiver: this.findField(message.messageFields, 56),
        sendingTime: this.findField(message.messageFields, 52),
        ordStatus: this.findField(message.messageFields, 39)
      }
    ));
  }
  @Input()
  selectedMessage: FixMessage;

  @Output()
  onMessageSelection: EventEmitter<FixMessage> = new EventEmitter<FixMessage>();

  findField(messageFields: FixPair[], tag: number): FixPair {
    return messageFields.find(fixPair => fixPair.fixField.tag === tag);
  }

  getOrdStatusStyle(message: FixMessageView): string {
    const style = this.ORD_STATUS_STYLE[message.ordStatus?.fixValue?.value];
    return isNil(style) ? 'fieldcolumn-grey' : style;
  }

  getOrdTypeStyle(message: FixMessageView): string {
    const style = this.ORD_TYPE_STYLE[message?.messageType?.fixValue?.value];
    return isNil(style) ? 'fieldcolumn-grey' : style;
  }

  getRowHighlight(message: FixMessageView): string {
    return message.messageId === this.selectedMessage.messageId ? 'selectedRow' : '';
  }

}
