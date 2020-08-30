import {Component, Input, OnInit} from '@angular/core';
import {FixMessage} from '../../model/fix-message.model';
import {ComparatorService} from './comparator.service';

@Component({
  selector: 'app-message-comparator',
  templateUrl: './message-comparator.component.html',
  styleUrls: ['./message-comparator.component.scss']
})
export class MessageComparatorComponent implements OnInit {

  _messages: FixMessage[];
  highlightedRows: number[];

  @Input()
  set messages(messages: FixMessage[]) {
    this._messages = messages;
    if (messages && messages.length > 1) {
      this.firstMessage = messages[0];
      this.secondMessage = messages[1];
      this.highlightedRows = this.comparatorService.compare(this.firstMessage, this.secondMessage);
    }
  }

  firstMessage: FixMessage;
  secondMessage: FixMessage;

  constructor(private comparatorService: ComparatorService) {
  }

  ngOnInit(): void {
  }

  onFirstMessageSelected(fixMessage: FixMessage): void {
    this.firstMessage = fixMessage;
    this.highlightedRows = this.comparatorService.compare(this.firstMessage, this.secondMessage);
  }

  onSecondMessageSelected(fixMessage: FixMessage): void {
    this.secondMessage = fixMessage;
    this.highlightedRows = this.comparatorService.compare(this.firstMessage, this.secondMessage);
  }

}
