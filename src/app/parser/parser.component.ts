import {Component, OnInit} from '@angular/core';
import {isEmpty} from 'lodash';
import {FormControl} from '@angular/forms';
import {ParserService} from './parser.service';
import {FixMessage} from '../model/fix-message.model';
import {FixPair} from '../model/fix-pair.model';

@Component({
  templateUrl: './parser.component.html',
  styleUrls: ['./parser.component.scss']
})
export class ParserComponent implements OnInit {

  private static readonly SAMPLE = '8=FIX.4.2#9=123#35=D#49=BlazarQuant#56=Broker#52=20160325-22:52:30.530#11=1001#38=200#40=1#54=1#55=IBM#59=0#10=013#' +
    '8=FIX.4.2#9=157#35=8#49=Broker#56=BlazarQuant#52=20160325-22:52:30.532#6=0#11=1001#14=0#17=1#20=0#31=0#32=0#37=1001#38=200#39=0#40=1#54=1#55=IBM#59=0#60=20160325-22:52:30.534#150=0#151=200#10=121#' +
    '8=FIX.4.2#9=180#35=8#49=Broker#56=BlazarQuant#52=20160325-22:52:30.534#6=476.5000#11=1001#14=53.00#17=2#20=0#31=476.50#32=53.00#37=1001#38=200#39=1#40=1#54=1#55=IBM#59=0#60=20160325-22:52:30.534#150=1#151=147.00#10=006#' +
    '8=FIX.4.2#9=180#35=8#49=Broker#56=BlazarQuant#52=20160325-22:52:30.535#6=476.5000#11=1001#14=200.00#17=3#20=0#31=476.50#32=147.00#37=1001#38=200#39=2#40=1#54=1#55=IBM#59=0#60=20160325-22:52:30.535#150=2#151=0.00#10=253#' +
    '8=FIX.4.2#9=123#35=D#49=BlazarQuant#56=Broker#52=20160325-22:53:04.092#11=1002#38=100#40=2#44=420#54=1#55=GE#59=0#10=064#' +
    '8=FIX.4.2#9=167#35=8#49=Broker#56=BlazarQuant#52=20160325-22:53:04.094#6=0#11=1002#14=0#17=4#20=0#31=0#32=0#37=1002#38=100#39=0#40=2#44=420.00#54=1#55=GE#59=0#60=20160325-22:53:04.094#150=0#151=100#10=089#' +
    '8=FIX.4.2#9=123#35=G#49=BlazarQuant#56=Broker#52=20160325-22:52:12.222#11=1003#41=1002#38=100#40=2#44=450#54=1#55=GE#59=0#10=064#' +
    '8=FIX.4.2#9=175#35=8#49=Broker#56=BlazarQuant#52=20160325-22:53:12.225#6=0#11=1003#14=0#17=5#20=0#31=0#32=0#37=1003#38=100#39=5#40=2#41=1002#44=450.00#54=1#55=GE#59=0#60=20160325-22:53:12.225#150=5#151=100#10=196#' +
    '8=FIX.4.2#9=187#35=8#49=Broker#56=BlazarQuant#52=20160325-22:53:12.225#6=447.3000#11=1003#14=1.00#17=6#20=0#31=447.30#32=1.00#37=1003#38=100#39=1#40=2#44=450.00#54=1#55=GE#59=0#60=20160325-22:53:12.225#150=1#151=99.00#10=071#' +
    '8=FIX.4.2#9=189#35=8#49=Broker#56=BlazarQuant#52=20160325-22:53:12.226#6=447.3000#11=1003#14=100.00#17=7#20=0#31=447.30#32=99.00#37=1003#38=100#39=2#40=2#44=450.00#54=1#55=GE#59=0#60=20160325-22:53:12.226#150=2#151=0.00#10=173#' +
    '8=FIX.4.2#9=123#35=D#49=BlazarQuant#56=Broker#52=20160325-23:03:34.784#11=1004#38=100#40=2#44=100#54=1#55=KO#59=0#10=064#' +
    '8=FIX.4.2#9=167#35=8#49=Broker#56=BlazarQuant#52=20160325-23:03:34.785#6=0#11=1004#14=0#17=1#20=0#31=0#32=0#37=1004#38=380#39=0#40=2#44=100.00#54=2#55=KO#59=0#60=20160325-23:03:34.786#150=0#151=380#10=106#' +
    '8=FIX.4.2#9=123#35=F#49=BlazarQuant#56=Broker#52=20160325-23:03:39.526#11=1005#41=1004#55=KO#59=0#10=064#' +
    '8=FIX.4.2#9=160#35=8#49=Broker#56=BlazarQuant#52=20160325-23:03:39.527#6=0#11=1005#14=0#17=2#20=0#31=0#32=0#37=1005#38=380#39=4#41=1001#54=2#55=KO#59=0#60=20160325-23:03:39.527#150=4#151=380#10=055#';

  messagesFormControl: FormControl;
  messages: FixMessage[];
  selectedMessage: FixMessage;

  messagesColumns: string[] = ['id', 'sender', 'receiver', 'sendingTime', 'msgType', 'summary'];
  messageColumns: string[] = ['tag', 'name', 'value', 'description'];

  styledTags: number[] = [4, 5, 18, 20, 35, 39, 40, 54, 59, 102, 103, 150];

  constructor(private parserService: ParserService) {
  }

  ngOnInit(): void {
    this.messagesFormControl = new FormControl('');
  }


  onSampleButtonClick(): void {
    this.messagesFormControl.setValue(ParserComponent.SAMPLE);
  }

  onParseButtonClick(): void {
    this.parserService.parse({
      username: 'test',
      input: this.messagesFormControl.value,
      dictionaryDescriptor: {
        loaderType: 'QUICKFIX_LOADER',
        providerName: 'FIX4.2'
      }
    }).subscribe((messages: FixMessage[]) => {
      this.messages = messages;
      if (!isEmpty(messages)) {
        this.selectedMessage = messages[0];
      }
    });
  }

  getSender(fixMessage: FixMessage): string {
    return this.findField(fixMessage.messageFields, 49)?.fixValue?.value;
  }

  getReceiver(fixMessage: FixMessage): string {
    return this.findField(fixMessage.messageFields, 56)?.fixValue?.value;
  }

  getSendingTime(fixMessage: FixMessage): string {
    return this.findField(fixMessage.messageFields, 52)?.fixValue?.value;
  }

  getMsgType(fixMessage: FixMessage): string {
    return this.findField(fixMessage.messageFields, 35)?.fixValue?.description;
  }

  getSummary(fixMessage: FixMessage): string {
    return this.findField(fixMessage.messageFields, 39)?.fixValue?.description;
  }

  findField(messageFields: FixPair[], tag: number): FixPair {
    return messageFields.find(fixPair => fixPair.fixField.tag === tag);
  }

  getMessage(selectedMessage: FixMessage): void {
    this.selectedMessage = selectedMessage;
  }

  getTagStyle(tag: number): boolean {
    return this.styledTags.includes(tag);
  }

}
