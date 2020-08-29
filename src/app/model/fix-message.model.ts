import {FixVersion} from './fix-version.enum';
import {FixPair} from './fix-pair.model';

export interface FixMessage {

  messageId: number;
  version: FixVersion;
  messageType: FixPair;
  messageFields: FixPair[];

}
