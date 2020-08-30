import {LoaderType} from './loader-type.enum';

export interface DictionaryDescriptor {
  loaderType: LoaderType;
  providerName: string;
}
