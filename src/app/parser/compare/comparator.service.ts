import {isNil} from 'lodash';
import {FixMessage} from '../../model/fix-message.model';
import {Injectable} from '@angular/core';

@Injectable()
export class ComparatorService {

  compare(firstMessage: FixMessage, secondMessage: FixMessage): number[] {
    return secondMessage.messageFields
      .filter(messageField => {
        const fixPair = firstMessage.messageFields.find(field => messageField.fixField?.tag === field.fixField?.tag);
        return isNil(fixPair) || fixPair.fixValue?.value !== messageField.fixValue?.value;
      })
      .map(messageField => messageField.fixField.tag);
  }

}
